package com.zhxia.quartz.domain;

public class JobConst {
	// job操作
	public static final int JOB_OP_START = 1;
	public static final int JOB_OP_STOP = 2;
	public static final int JOB_OP_RELOAD = 3;

	// job状态
	public static final int JOB_STATUS_INIT = 0;
	public static final int JOB_STATUS_RUN = 1;
	public static final int JOB_STATUS_STOP = 2;

	public static final String JOB_COMMAND = "cmd"; // job command
	public static final String JOB_PARAM_KEY_COMMAND = "command";
	public static final String JOB_PARAM_KEY_PARAMETERS = "params";
	public static final String JOB_PARAM_KEY_CONSUME_STREAM = "cs";
	public static final String JOB_PARAM_KEY_WAIT_FLAG = "wait";
	public static final String JOB_CATE = "cate";
	public static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	public static final String JOB_BIZ_NAME = "jobBiz";
	public static final int JOB_CAT_JAVA = 1; // java job
	public static final int JOB_CAT_SCRIPT = 2; // script job,eg:php、python
	public static final int JOB_CAT_SHELL = 3; // shell job
}
