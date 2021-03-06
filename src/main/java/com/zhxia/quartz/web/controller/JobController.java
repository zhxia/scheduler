package com.zhxia.quartz.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhxia.quartz.domain.JobBiz;
import com.zhxia.quartz.model.JobModel;
import com.zhxia.quartz.plugin.JobLoaderPlugin;
import com.zhxia.quartz.util.Common;
import com.zhxia.quartz.util.Task;
import com.zhxia.quartz.util.TaskQueue;

@Controller
@RequestMapping(value = "/job")
public class JobController {

	@Resource(name = "jobBiz")
	private JobBiz jobBiz;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listJob(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		List<JobModel> list = jobBiz.getJobListByUserId(userId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("list", list);
		Map<String, String> urlList = (Map<String, String>) request.getAttribute("urlList");
		String addUrl = urlList.get("urlJobAdd");
		model.put("addUrl", addUrl);
		return new ModelAndView("/job/list", model);
	}

	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String addJob(HttpServletRequest request) {
		if (request.getMethod() == RequestMethod.POST.name()) {
			String jobName = request.getParameter("jobName").trim();
			String jobGroup = request.getParameter("jobGroup").trim();
			String description = request.getParameter("description").trim();
			int priority = Integer.parseInt(request.getParameter("priority"));
			int jobCategory = Integer.parseInt(request.getParameter("jobCategory"));
			String command = request.getParameter("command").trim();
			String cronExpression = request.getParameter("cronExpression").trim();
			Map<String, Serializable> data = new HashMap<>();
			data.put("jobName", jobName);
			data.put("jobGroup", jobGroup);
			data.put("description", description);
			data.put("priority", priority);
			data.put("jobCategory", jobCategory);
			data.put("command", command);
			data.put("cronExpression", cronExpression);
			jobBiz.addJob(data);
			return "redirect:/job/list.do?userId=0";
		}
		return "/job/add";
	}

	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String editJob(HttpServletRequest request, @RequestParam("jobId") int jobId, Model model) {
		JobModel job;
		String method = request.getMethod();
		if (method == RequestMethod.GET.name()) {
			job = jobBiz.getJobDetail(jobId);
			model.addAttribute("job", job);
		} else if (method == RequestMethod.POST.name()) {
			String priority = request.getParameter("priority");
			String jobCategory = request.getParameter("jobCategory");
			String description = request.getParameter("description").trim();
			String cronExpression = request.getParameter("cronExpression").trim();
			String command = request.getParameter("command").trim();
			Map<String, Serializable> data = new HashMap<String, Serializable>();
			data.put("priority", priority);
			data.put("jobCategory", jobCategory);
			data.put("description", description);
			data.put("cronExpression", cronExpression);
			data.put("command", command);
			jobBiz.editJob(data, jobId);
			return "redirect:/job/list";
		}
		return "/job/edit";
	}

	@RequestMapping(value = "/op", method = { RequestMethod.GET })
	public String jobOperation(@RequestParam("jobId") int jobId, @RequestParam("op") int op) {
		JobModel jobModel = jobBiz.getJobDetail(jobId);
		if (null != jobModel) {
			TaskQueue taskQueue = JobLoaderPlugin.getTaskQueue();
			Task task = new Task(jobModel, op);
			taskQueue.enQueue(task);
			int jobStatus = Common.getJobStatus(op);
			Map<String, Serializable> data = new HashMap<>();
			data.put("jobStatus", jobStatus);
			jobBiz.editJob(data, jobId);
		}
		return "redirect:/job/list?t=" + Math.random();
	}

}
