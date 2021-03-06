package com.zhxia.quartz.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zhxia.quartz.model.JobModel;

@SuppressWarnings("unchecked")
public class JobDao extends HibernateDaoSupport {

	public List<JobModel> getAllJobList(int userId, int status) {
		List<JobModel> list = null;
		DetachedCriteria where = DetachedCriteria.forClass(JobModel.class);
		Criterion criteria = Restrictions.ne("jobStatus", 0);
		if (userId > 0) {
			Restrictions.and(criteria, Restrictions.eq("userId", userId));
		}
		where.add(criteria);
		list = (List<JobModel>) this.getHibernateTemplate().findByCriteria(where);
		return list;

	}

	public List<JobModel> getJobList(int userId) {
		List<JobModel> list = null;
		String hqlString = String.format("from JobModel where userId=%d", userId);
		list = (List<JobModel>) this.getHibernateTemplate().find(hqlString);
		return list;
	}

	/**
	 * 获取单个job详细信息
	 * 
	 * @param jobId
	 * @return
	 */
	public JobModel getJobDetail(int jobId) {
		JobModel jobModel = null;
		jobModel = this.getHibernateTemplate().get(JobModel.class, jobId);
		return jobModel;
	}

	/**
	 * 添加job信息
	 * 
	 * @param job
	 * @return
	 */
	public int addJob(JobModel job) {
		return (Integer) this.getHibernateTemplate().save(job);
	}


	/**
	 * 更新job
	 * 
	 * @param jobModel
	 */
	public void editJob(JobModel jobModel) {
		this.getHibernateTemplate().update(jobModel);
	}

}
