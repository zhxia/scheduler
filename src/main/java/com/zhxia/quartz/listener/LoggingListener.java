package com.zhxia.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

public class LoggingListener extends JobListenerSupport {
	private final static String jobToBeFiredMessage = "Job {1}.{0} fired (by trigger id: {2}) at: {3, date, HH:mm:ss MM/dd/yyyy}";

	private final static String jobSuccessMessage = "Job {1}.{0} execution complete (by trigger id: {2}) at {3, date, HH:mm:ss MM/dd/yyyy} and result: {4}";

	private final static String jobFailedMessage = "Job {1}.{0} execution failed (by trigger id: {2}) at {3, date, HH:mm:ss MM/dd/yyyy} and result: {4} and reports: {5}";

	private final static String jobWasVetoedMessage = "Job {1}.{0} was vetoed.  It was to be fired (by trigger {2}) at: {3, date, HH:mm:ss MM/dd/yyyy}";

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String jobName = context.getJobDetail().getKey().getName();
		String jobGroup = context.getJobDetail().getKey().getGroup();
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		super.jobExecutionVetoed(context);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		super.jobWasExecuted(context, jobException);
	}
}
