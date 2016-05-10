package com.zhxia.quartz.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zhxia.quartz.domain.JobConst;

public abstract class CommandInvokerJob extends QuartzJobBean implements InterruptableJob {

	private Process proc = null;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void interrupt() throws UnableToInterruptJobException {
		logger.info("job is interrupting...");
		if (null != this.proc) {
			this.proc.destroy();
		}
	}

	/**
	 * 对Job中的参数进行整理
	 * 
	 * @param context
	 * @return
	 */
	protected abstract JobDataMap analyzeJobDataMap(JobExecutionContext context);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = analyzeJobDataMap(context);
		String strCmd = data.getString(JobConst.JOB_PARAM_KEY_COMMAND);
		String strParameters = data.getString(JobConst.JOB_PARAM_KEY_PARAMETERS);
		if (null == strCmd || strCmd.isEmpty()) {
			throw new JobExecutionException("Command executable file is not configured");
		}
		boolean wait = true;
		if (data.containsKey(JobConst.JOB_PARAM_KEY_WAIT_FLAG)) {
			wait = data.getBooleanValue(JobConst.JOB_PARAM_KEY_WAIT_FLAG);
		}
		boolean consumeStreams = false;
		if (data.containsKey(JobConst.JOB_PARAM_KEY_CONSUME_STREAM)) {
			consumeStreams = data.getBooleanValue(JobConst.JOB_PARAM_KEY_CONSUME_STREAM);
		}
		int exitCode = this.runCommand(strCmd, strParameters, wait, consumeStreams);
		context.setResult(exitCode);
	}

	protected Integer runCommand(String command, String parameters, boolean wait, boolean consumeStreams)
			throws JobExecutionException {
		Integer result = null;
		try {
			Runtime rt = Runtime.getRuntime();
			String cmd = String.format("%s %s", command, parameters);
			proc = rt.exec(cmd);
			if (consumeStreams) {
				// Consumes the stdout from the process
				StreamConsumer stdoutConsumer = new StreamConsumer(proc.getInputStream(), "stdout");
				stdoutConsumer.start();
			}
			// Consumes the stderr from the process
			StreamConsumer stderrConsumer = new StreamConsumer(proc.getErrorStream(), "stderr");
			stderrConsumer.start();
			if (wait) {
				result = proc.waitFor();
			}
			return result;
		} catch (Exception e) {
			throw new JobExecutionException("Run command error:", e, false);
		}
	}

	/**
	 * Consumes data from the given input stream until EOF and prints the data
	 * to stdout
	 *
	 * @author cooste
	 * @author jhouse
	 */
	class StreamConsumer extends Thread {
		InputStream is;
		String type;

		/**
		 *
		 */
		public StreamConsumer(InputStream inputStream, String type) {
			this.is = inputStream;
			this.type = type;
		}

		/**
		 * Runs this object as a separate thread, printing the contents of the
		 * InputStream supplied during instantiation, to either stdout or stderr
		 */
		@Override
		public void run() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(is));
				String line;

				while ((line = br.readLine()) != null) {
					if (type.equalsIgnoreCase("stderr")) {
						logger.warn(type + ">" + line);
					} else {
						logger.info(type + ">" + line);
					}
				}
			} catch (IOException ioe) {
				logger.error("Error consuming " + type + " stream of spawned process.", ioe);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception ignore) {
					}
				}
			}
		}
	}

}
