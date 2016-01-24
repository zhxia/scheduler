package com.zhxia.quartz.job;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zhxia.quartz.domain.JobConst;

public abstract class CommandInvokerJob extends QuartzJobBean implements
		InterruptableJob {

	private Process process = null;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	public void interrupt() throws UnableToInterruptJobException {
		log.info("job is interrupting...");
		if (null != this.process) {
			this.process.destroy();
		}
	}
	/**
	 * 对Job中的参数进行整理
	 * @param context
	 * @return
	 */
	protected abstract JobDataMap analyzeJobDataMap(JobExecutionContext context);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = analyzeJobDataMap(context);
		String strCmd = data.getString(JobConst.JOB_PARAM_KEY_COMMAND);
		String strParameters = data
				.getString(JobConst.JOB_PARAM_KEY_PARAMETERS);
		if (null == strCmd || strCmd.isEmpty()) {
			throw new JobExecutionException(
					"Command executable file is not configured");
		}
		boolean wait = true;
		if (data.containsKey(JobConst.JOB_PARAM_KEY_WAIT_FLAG)) {
			wait = data.getBooleanValue(JobConst.JOB_PARAM_KEY_WAIT_FLAG);
		}
		int exitCode = this.runCommand(strCmd, strParameters, wait);
		context.setResult(exitCode);
	}

	protected Integer runCommand(String command, String parameters, boolean wait)
			throws JobExecutionException {
		Integer result = null;
		try {
			Runtime rt = Runtime.getRuntime();
			String cmd = String.format("%s %s", command, parameters);
			process = rt.exec(cmd);
			if (wait) {
				result = process.waitFor();
			}
			return result;
		} catch (Exception e) {
			throw new JobExecutionException("Run command error:", e, false);
		}
	}

}
