package com.test.quartz.util;

import java.text.ParseException;
import java.util.HashMap;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.ClassLoadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.quartz.domain.JobConst;
import com.test.quartz.model.JobModel;

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

	public void scheduleJob(JobModel model, boolean replace) {
		JobDetail jobDetail = getJobDetail(model);
		if (jobDetail != null) {
			String strJobParameter = model.getParameters();
			logger.info("参数：" + strJobParameter);
			String[] arrParams = strJobParameter.split(" ", 2);
			java.util.Map<String, Object> data = new HashMap<String, Object>();
			data.put(JobConst.JOB_PARAM_KEY_COMMAND, arrParams[0]); // 设置脚本相关的参数
			data.put(JobConst.JOB_PARAM_KEY_PARAMETERS, arrParams[1]);
			jobDetail.getJobDataMap().putAll(data);
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
		}
	}

	protected Trigger getTrigger(JobModel model) {
		Trigger trigger = null;
		String cronExpression = model.getCronExpression();
		TriggerKey triggerKey = new TriggerKey(model.getId() + "");

		if (cronExpression == null || cronExpression.equals("")) {
			trigger = (SimpleTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity(triggerKey)
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withRepeatCount(0)).build();
		} else {
			if (!CronExpression.isValidExpression(cronExpression)) {
				logger.warn(String.format(
						"job:%s,group:%s,cronexpression:\"%s\" is invalid!",
						model.getJobName(), model.getJobGroup(), cronExpression));
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
						.build();
			} catch (ParseException e) {
				logger.error("bulild cron trigger faild");
			}
		}
		return trigger;
	}

	@SuppressWarnings("unchecked")
	protected JobDetail getJobDetail(JobModel model) {
		JobDetail jobDetail = null;
		Class<Job> jobClass = null;
		try {
			jobClass = (Class<Job>) classLoadHelper.loadClass(model
					.getJobClass());
			JobKey jobKey = new JobKey(model.getId() + "");
			jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey)
					.build();
		} catch (ClassNotFoundException e) {
			logger.error(String.format("load job class:%s faild!",
					model.getJobClass()));
		}
		return jobDetail;
	}
}