package com.zhxia.quartz.job;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zhxia.quartz.domain.JobConst;

/**
 * 执行一般的脚本，如：python,php /a.py aa bb
 * 
 * @author zhxia
 *
 */
public class ScriptJob extends CommandInvokerJob {

	/**
	 * 用于解析job中的cmd参数
	 * 
	 * @param cmd
	 * @return
	 * @throws JobExecutionException
	 */
	private Map<String, String> analyzeCommand(String cmd) throws JobExecutionException {
		Map<String, String> rt = new HashMap<String, String>();
		String[] arrCmd = cmd.split(" ", 3);
		List<String> listCmd = Arrays.asList(arrCmd);
		if (listCmd.size() < 2) {
			throw new JobExecutionException("Job command params not correct!");
		}
		if (listCmd.size() == 2) {
			listCmd.add("");
			arrCmd = listCmd.toArray(new String[] {});
		}

		String command = arrCmd[0];
		String params = String.format("%s %s", arrCmd[1], arrCmd[2]);
		rt.put(JobConst.JOB_PARAM_KEY_COMMAND, command);
		rt.put(JobConst.JOB_PARAM_KEY_PARAMETERS, params);
		return rt;
	}

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
