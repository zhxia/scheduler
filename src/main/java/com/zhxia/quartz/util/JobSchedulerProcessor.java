package com.zhxia.quartz.util;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.ClassLoadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhxia.quartz.domain.JobConst;
import com.zhxia.quartz.model.JobModel;

public class JobSchedulerProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(JobSchedulerProcessor.class);

	private Scheduler scheduler = null;
	private ClassLoadHelper classLoadHelper = null;

	public JobSchedulerProcessor(Scheduler scheduler,
			ClassLoadHelper classLoadHelper) {
		this.scheduler = scheduler;
		this.classLoadHelper = classLoadHelper;
	}

	public void scheduleJob(JobModel model, boolean replace)
			throws JobExecutionException {
		JobDetail jobDetail = getJobDetail(model);
		if (jobDetail != null) {
			try {
				scheduler.addJob(jobDetail, replace);
				Trigger trigger = getTrigger(model);
				if (trigger != null) {
					Trigger oldTrigger = scheduler.getTrigger(trigger.getKey());
					if (oldTrigger == null) {
						logger.info(
								"oldTrigger is null, scheduleJob by new trigger %s",
								trigger.getDescription());
						scheduler.scheduleJob(trigger);
					} else {
						logger.info(
								"oldTrigger is not null, rescheduleJob by new trigger %s",
								trigger.getDescription());
						scheduler.rescheduleJob(oldTrigger.getKey(), trigger);
					}
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("no job found!");
		}
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	protected Trigger getTrigger(JobModel model) {
		Trigger trigger = null;
		String cronExpression = model.getCronExpression();
		TriggerKey triggerKey = new TriggerKey(model.getId() + "",
				model.getId() + "");

		if (!CronExpression.isValidExpression(cronExpression)) {
			logger.warn(String.format(
					"job:%s,group:%s,cronexpression:\"%s\" is invalid!",
					model.getJobName(), model.getJobGroup(), cronExpression));
			return null;
		}
		CronExpression cronCronExpression;
		try {
			cronCronExpression = new CronExpression(cronExpression);
			trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerKey)
					.withSchedule(
							CronScheduleBuilder
									.cronSchedule(cronCronExpression))
					.forJob(model.getId() + "", model.getId() + "").build();
		} catch (ParseException e) {
			logger.error("bulild cron trigger faild");
			return null;
		}
		return trigger;
	}

	/**
	 * 创建job，并设置相关的参数
	 * 
	 * @param model
	 * @return
	 * @throws JobExecutionException
	 */

	@SuppressWarnings("unchecked")
	protected JobDetail getJobDetail(JobModel model)
			throws JobExecutionException {
		JobDetail jobDetail = null;
		Class<Job> jobClass = null;
		try {
			jobClass = (Class<Job>) classLoadHelper.loadClass(model
					.getJobClass());
			JobKey jobKey = new JobKey(model.getId() + "", model.getId() + "");
			JobDataMap jobDataMap = new JobDataMap();
			String jobCommand = model.getCommand();
			if (null == jobCommand || jobCommand.isEmpty()) {
				throw new JobExecutionException(String.format(
						"job id:%s command field not set!", model.getId()));
			}
			jobDataMap.put(JobConst.JOB_COMMAND, jobCommand);
			jobDataMap.put(JobConst.JOB_CATE, model.getJobCategory());
			jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey)
					.usingJobData(jobDataMap).storeDurably().build();
		} catch (ClassNotFoundException e) {
			logger.error(String.format("load job class:%s faild!",
					model.getJobClass()));
		}
		return jobDetail;
	}
}