package com.zhxia.quartz.domain;

public class JobConst {

	public static final String JOB_PARAM_KEY_COMMAND = "command";
	public static final String JOB_PARAM_KEY_PARAMETERS = "params";
	public static final String JOB_PARAM_KEY_WAIT_FLAG = "wait";

	public static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	public static final String JOB_BIZ_NAME = "jobBiz";

	public static final int JOB_CAT_JAVA = 1; // java 脚本
	public static final int JOB_CAT_PHP = 2; // php 脚本
	public static final int JOB_CAT_SHELL = 3; // shell 脚本

}
