package com.zhxia.quartz.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zhxia.quartz.domain.JobConst;

public class ShellJob extends CommandInvokerJob {

	/**
	 * 
	 * @param cmd
	 * @return
	 * @throws JobExecutionException
	 */
	private Map<String, String> analyzeCommand(String cmd)
			throws JobExecutionException {
		Map<String, String> rt = new HashMap<String, String>();
		String[] arrCmd = cmd.split(" ", 2);
		if (arrCmd.length < 2) {
			throw new JobExecutionException("Job command params not correct!");
		}
		String command = arrCmd[0];
		String params = arrCmd[1];
		rt.put(JobConst.JOB_PARAM_KEY_COMMAND, command);
		rt.put(JobConst.JOB_PARAM_KEY_PARAMETERS, params);
		return rt;
	}

	@Override
	protected JobDataMap analyzeJobDataMap(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		String jobCommand = jobDataMap.getString(JobConst.JOB_COMMAND);
		Map<String, String> mapData;
		try {
			mapData = analyzeCommand(jobCommand);
			jobDataMap.putAll(mapData);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}

		return jobDataMap;
	}

}
