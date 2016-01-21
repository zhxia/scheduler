package com.zhxia.quartz.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class jobTest extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(jobTest.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("jobTest is running....");
		JobKey jobKey =context.getJobDetail().getKey();
		log.info("jobkey:"+jobKey+" exexute at "+new Date());
        log.info("============================");
	}

   /* @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        log.info("jobTest is running....");
        log.info("============================");
    }*/
    

}
