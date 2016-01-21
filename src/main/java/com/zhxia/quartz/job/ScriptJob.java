package com.zhxia.quartz.job;

import java.util.HashMap;
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

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = context.getMergedJobDataMap();
		String originCommand = data.getString(JobConst.JOB_PARAM_KEY_COMMAND);
		String[] tmpArr = originCommand.split(" ", 2);
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put(JobConst.JOB_PARAM_KEY_COMMAND, tmpArr[0]);
		mapData.put(JobConst.JOB_PARAM_KEY_PARAMETERS, tmpArr[1]);
		context.getJobDetail().getJobDataMap().putAll(mapData);
		super.executeInternal(context);
	}

}
