package com.zhxia.quartz.domain;

public class JobConst {
	public static final int JOB_OP_PAUSE = 1;
	public static final int JOB_OP_RESUME = 2;
	
	public static final String JOB_COMMAND = "cmd";
	public static final String JOB_PARAM_KEY_COMMAND = "command";
	public static final String JOB_PARAM_KEY_PARAMETERS = "params";
	public static final String JOB_PARAM_KEY_CONSUME_STREAM = "cs";
	public static final String JOB_PARAM_KEY_WAIT_FLAG = "wait";
	public static final String JOB_CATE = "cate";
	public static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	public static final String JOB_BIZ_NAME = "jobBiz";
	public static final int JOB_CAT_JAVA = 1; // java 脚本
	public static final int JOB_CAT_SCRIPT = 2; // script 脚本
	public static final int JOB_CAT_SHELL = 3; // shell脚本
}

