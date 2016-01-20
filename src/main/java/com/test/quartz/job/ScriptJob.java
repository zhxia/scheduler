package com.test.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.test.quartz.domain.JobConst;

/**
 * 执行一般的脚本，如：python,php
 * @author zhxia
 *
 */
public class ScriptJob extends CommandInvokerJob {

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap data=context.getMergedJobDataMap();
        String scriptBin=data.getString(JobConst.SCRIPT_BIN);
        if(null==scriptBin||scriptBin.isEmpty()){
            throw new JobExecutionException("script bin path not setted!");
        }
        super.executeInternal(context);
    }

}
