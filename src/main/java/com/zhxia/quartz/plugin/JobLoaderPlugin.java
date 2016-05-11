package com.zhxia.quartz.plugin;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.zhxia.quartz.domain.JobBiz;
import com.zhxia.quartz.domain.JobConst;
import com.zhxia.quartz.model.JobModel;
import com.zhxia.quartz.util.Common;
import com.zhxia.quartz.util.JobSchedulerProcessor;
import com.zhxia.quartz.util.Task;
import com.zhxia.quartz.util.TaskQueue;

public class JobLoaderPlugin implements SchedulerPlugin, Runnable {

	private static final Logger logger = LoggerFactory.getLogger(JobLoaderPlugin.class);

	private ScheduledExecutorService scheduledExecutor = null;

	private int scanIntval = 5000; // 用于自动扫描新增的任务，间隔时间单位位毫秒

	private Scheduler scheduler = null;

	private static JobSchedulerProcessor jobSchedulerProcessor;

	private String applicationContextKey = JobConst.APPLICATION_CONTEXT_KEY;

	private JobBiz jobBiz = null;

	private static TaskQueue taskQueue = null;

	public static TaskQueue getTaskQueue() {
		return taskQueue;
	}

	public void setScanIntval(int scanIntval) {
		this.scanIntval = scanIntval;
	}

	public void setApplicationContextKey(String applicationContextKey) {
		this.applicationContextKey = applicationContextKey;
	}

	/**
	 * 定时扫描任务队列，并处理任务
	 */
	public void run() {
		logger.info("begin to exeute job task");
		if (taskQueue.isEmpty()) {
			logger.info("task queue is empty!");
			return;
		}
		Task task = null;
		while (null != (task = taskQueue.deQueue())) {
			JobModel jobModel = task.getJobModel();
			int jobOperation = task.getOperation();
			int jobId = jobModel.getId();
			logger.info("JobId:" + jobId + ",Job Operation:" + jobOperation);
			if (jobOperation == JobConst.JOB_OP_START) {
				jobSchedulerProcessor.startJob(jobModel, true);
			} else if (jobOperation == JobConst.JOB_OP_STOP) {
				jobSchedulerProcessor.pauseJob(jobId);
			} else if (jobOperation == JobConst.JOB_OP_RELOAD) {
				jobSchedulerProcessor.startJob(jobModel, true);
			}
			String operationName = Common.getJobOperationName(jobOperation);
			logger.info(String.format("job operation:[%s],job detail:%s", operationName, jobModel.toString()));
		}
	}

	public void initialize(String name, Scheduler scheduler, ClassLoadHelper classLoadHelper)
			throws SchedulerException {
		this.scheduler = scheduler;
		jobSchedulerProcessor = new JobSchedulerProcessor(scheduler, classLoadHelper);
		taskQueue = new TaskQueue();
		logger.info("jobLoader plugin is initializing...");
	}

	public void start() {
		logger.info("jobLoader is running...");
		initializeJobs();
		if (scanIntval > 0) {
			scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutor.scheduleAtFixedRate(this, 0, scanIntval, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * 扫描数据库中的job并自动增加到待处理的任务队列
	 */
	private void initializeJobs() {
		ApplicationContext applicationContext = null;
		try {
			applicationContext = (ApplicationContext) scheduler.getContext().get(applicationContextKey);
			jobBiz = (JobBiz) applicationContext.getBean(JobConst.JOB_BIZ_NAME);
		} catch (SchedulerException e) {
			logger.error("ApplicationContext is null!");
			return;
		}
		try {
			List<JobModel> jobList = jobBiz.getJobList(JobConst.JOB_STATUS_INIT);
			for (JobModel jobModel : jobList) {
				logger.info("initialize job from database...");
				Task task = new Task(jobModel, JobConst.JOB_OP_START);
				taskQueue.enQueue(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		logger.info("jobLoader is shutdown !");
		if (scheduledExecutor != null) {
			scheduledExecutor.shutdown();
		}
	}
}
