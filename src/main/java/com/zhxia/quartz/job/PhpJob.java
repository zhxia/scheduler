package com.zhxia.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PhpJob extends CommandInvokerJob {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		super.executeInternal(context);
	}

}
