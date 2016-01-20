package com.test.quartz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class JobModel {
    @Id
    @Column(name = "id")
    private int Id;

    @Column(name = "user_id",columnDefinition="INT default 0")
    private int userId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "description",nullable=true)
    private String description;

    @Column(name = "priority",columnDefinition="INT default 5")
    private int priority;

    @Column(name = "job_class",nullable=true)
    private String jobClass;

    @Column(name = "job_category")
    private int jobCategory;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name="parameters")
    private String parameters;

    @Column(name = "job_status")
    private int jobStatus;

    @Column(name = "created")
    private int created;

    @Column(name = "last_update",nullable=true)
    private String lastUpdate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public int getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(int jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

}
