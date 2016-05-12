package com.zhxia.quartz.listener;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.listeners.TriggerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTriggerListener extends TriggerListenerSupport {
	private final static Logger logger = LoggerFactory.getLogger(JobTriggerListener.class);

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		long fireTime = System.currentTimeMillis();
		String message = String.format("Job ID:%s, fire at:%s", trigger.getKey().getName(), new Date(fireTime));
		logger.info(message);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return super.vetoJobExecution(trigger, context);
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		String message=String.format("Job ID:%s is miss fired",trigger.getKey().getName() );
		logger.warn(message);
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		long completedTime = System.currentTimeMillis();
		String message=String.format("Job ID:%s,complete at:%s", trigger.getKey().getName(),new Date(completedTime));
		logger.info(message);
	}

}
