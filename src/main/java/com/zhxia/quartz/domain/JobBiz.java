package com.zhxia.quartz.domain;

import java.util.List;
import java.util.Map;

import com.zhxia.quartz.dao.JobDao;
import com.zhxia.quartz.model.JobModel;

public class JobBiz {
    private JobDao jobDao;

    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    public List<JobModel> getJobListByUserId(int userId){
        return jobDao.getJobList(userId);
    }

    public JobModel getJobDetail(int jobId){
        return jobDao.getJobDetail(jobId);
    }

    public int addJob(JobModel job){
        return jobDao.addJob(job);
    }

    public void editJob(Map<String,String> data,int jobId){
        jobDao.editJob(data,jobId);
    }

}
