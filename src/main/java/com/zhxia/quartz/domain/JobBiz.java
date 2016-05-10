package com.zhxia.quartz.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zhxia.quartz.dao.JobDao;
import com.zhxia.quartz.model.JobModel;
import com.zhxia.quartz.util.Common;

public class JobBiz {
	private JobDao jobDao;

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public List<JobModel> getJobList(int status) {
		return jobDao.getAllJobList(0, status);
	}

	public List<JobModel> getJobListByUserId(int userId) {
		return jobDao.getJobList(userId);
	}

	public JobModel getJobDetail(int jobId) {
		return jobDao.getJobDetail(jobId);
	}

	public int addJob(Map<String, Serializable> data) {
		if(null==data) return 0;
		JobModel jobModel = new JobModel();
		String jobName = (String) data.get("jobName");
		int priority = (Integer) data.get("priority");
		String description = (String) data.get("description");
		int jobCategory = (Integer) data.get("jobCategory");
		String command = (String) data.get("command");
		String cronExpression = (String) data.get("cronExpression");
		String jobGroup = (String) data.get("jobGroup");
		jobModel.setJobName(jobName);
		jobModel.setPriority(priority);
		jobModel.setJobGroup(jobGroup);
		jobModel.setDescription(description);
		jobModel.setJobCategory(jobCategory);
		jobModel.setCommand(command);
		jobModel.setCronExpression(cronExpression);
		String jobClass = Common.getJobClass(jobCategory);
		jobModel.setJobClass(jobClass);
		return jobDao.addJob(jobModel);
	}

	public void editJob(Map<String, Serializable> data, int jobId) {
		if (null == data)
			return;
		JobModel jobModel = jobDao.getJobDetail(jobId);
		if(null==jobModel) return;
		jobModel.setId(jobId);
		if (data.containsKey("priority")) {
			int priority = Integer.valueOf((String) data.get("priority"));
			jobModel.setPriority(priority);
		}
		if (data.containsKey("jobCategory")) {
			int jobCategory = Integer.valueOf((String) data.get("jobCategory"));
			jobModel.setJobCategory(jobCategory);
		}
		if (data.containsKey("description")) {
			String description = (String) data.get("description");
			jobModel.setDescription(description);
		}
		if (data.containsKey("cronExpression")) {
			String cronExpression = (String) data.get("cronExpression");
			jobModel.setCronExpression(cronExpression);
		}
		if (data.containsKey("command")) {
			String command = (String) data.get("command");
			jobModel.setCommand(command);
		}
		if(data.containsKey("jobStatus")){
			int jobStatus=(Integer)data.get("jobStatus");
			jobModel.setJobStatus(jobStatus);
		}
		jobDao.editJob(jobModel);
	}

}
