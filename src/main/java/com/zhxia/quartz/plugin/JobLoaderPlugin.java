package com.zhxia.quartz.plugin;

import java.util.Date;
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
import com.zhxia.quartz.util.JobSchedulerProcessor;

public class JobLoaderPlugin implements SchedulerPlugin, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(JobLoaderPlugin.class);

    private ScheduledExecutorService scheduledExecutor = null;

    private int scanIntval = 5000; // 用于自动扫描新增的任务，间隔时间单位位毫秒

    private Scheduler scheduler = null;

    private JobSchedulerProcessor jobSchedulerProcessor = null;

    private String applicationContextKey = JobConst.APPLICATION_CONTEXT_KEY;

    private JobBiz jobBiz = null;

    public void setScanIntval(int scanIntval) {
        this.scanIntval = scanIntval;
    }

    public void setApplicationContextKey(String applicationContextKey) {
        this.applicationContextKey = applicationContextKey;
    }

    public void run() {
        logger.info(String.format("checking new task at:%s", new Date().toString()));
        scanForNewJobs();
    }

    public void initialize(String name, Scheduler scheduler, ClassLoadHelper classLoadHelper)
            throws SchedulerException {
        this.scheduler = scheduler;
        jobSchedulerProcessor = new JobSchedulerProcessor(scheduler, classLoadHelper);
        logger.info("jobLoader plugin is initializing...");
    }

    public void start() {
        logger.warn("jobLoader is run !");
        scanForNewJobs();
        if (scanIntval > 0) {
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutor.scheduleAtFixedRate(this, 0, scanIntval, TimeUnit.MILLISECONDS);
		}
    }

    /**
     * 扫描数据库中的job并自动增加
     */
    private void scanForNewJobs() {
        ApplicationContext applicationContext = null;
        try {
            applicationContext = (ApplicationContext) scheduler.getContext().get(applicationContextKey);
            jobBiz = (JobBiz) applicationContext.getBean(JobConst.JOB_BIZ_NAME);
        } catch (SchedulerException e) {
            logger.error("ApplicationContext is null!");
            return;
        }
        try {
            List<JobModel> jobList = jobBiz.getJobList(JobConst.JOB_STATUS_RUN);
            for (JobModel job : jobList) {
                System.out.println("@@@@@@@@@@@@@@@cron:" + job.getCronExpression());
                jobSchedulerProcessor.scheduleJob(job, true);
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
