package com.zhxia.quartz.util;

import com.zhxia.quartz.model.JobModel;

public class Task {
	private JobModel jobModel;
	private int operation;
	
	public Task(JobModel jobModel, int operation) {
		this.jobModel = jobModel;
		this.operation = operation;
	}

	public JobModel getJobModel() {
		return jobModel;
	}

	public void setJobModel(JobModel jobModel) {
		this.jobModel = jobModel;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

}
