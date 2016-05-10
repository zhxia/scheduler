package com.zhxia.quartz.util;

import com.zhxia.quartz.domain.JobConst;

public class Common {
	/**
	 * 根据脚本类型获取对应的脚本基类名称
	 * 
	 * @param jobType
	 * @return
	 */
	public static String getJobClass(int jobType) {
		String jobClass;
		switch (jobType) {
		case JobConst.JOB_CAT_SCRIPT:
			jobClass = "com.zhxia.quartz.job.ScriptJob";
			break;
		case JobConst.JOB_CAT_SHELL:
			jobClass = "com.zhxia.quartz.job.ShellJob";
			break;
		default:
			jobClass = "com.zhxia.quartz.job.JavaJob";
			break;
		}
		return jobClass;
	}

	/**
	 * 根据操作类型，返回预取的操作结果
	 * 
	 * @param operation
	 * @return
	 */
	public static int getJobStatus(int operation) {
		int status = JobConst.JOB_STATUS_INIT;
		switch (operation) {
		case JobConst.JOB_OP_START:
			status = JobConst.JOB_STATUS_RUN;
			break;
		case JobConst.JOB_OP_STOP:
			status = JobConst.JOB_STATUS_STOP;
			break;
		case JobConst.JOB_OP_RELOAD:
			status = JobConst.JOB_STATUS_RUN;
			break;
		}
		return status;
	}

	public static String getJobOperationName(int operation) {
		String result = "UNKOWN";
		switch (operation) {
		case JobConst.JOB_OP_START:
			result = "START";
			break;
		case JobConst.JOB_OP_STOP:
			result = "STOP";
			break;
		case JobConst.JOB_OP_RELOAD:
			result = "RELOAD";
			break;
		}
		return result;
	}
}
