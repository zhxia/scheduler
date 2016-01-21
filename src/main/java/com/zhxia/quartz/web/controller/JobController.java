package com.zhxia.quartz.web.controller;

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

@Controller
@RequestMapping(value = "/job")
public class JobController {

    @Resource(name = "jobBiz")
    private JobBiz jobBiz;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listJob(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getSession()
                .getAttribute("userId").toString());
        List<JobModel> list = jobBiz.getJobListByUserId(userId);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("list", list);
        Map<String, String> urlList = (Map<String, String>) request
                .getAttribute("urlList");
        String addUrl = urlList.get("urlJobAdd");
        model.put("addUrl", addUrl);
        return new ModelAndView("/job/list", model);
    }

    @RequestMapping(value = "/add", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String addJob(HttpServletRequest request) {
        if (request.getMethod() == RequestMethod.POST.name()) {
            JobModel job = new JobModel();
            String jobName = request.getParameter("jobName").trim();
            String jobGroup = request.getParameter("jobGroup").trim();
            String description = request.getParameter("description").trim();
            int priority = Integer.parseInt(request.getParameter("priority"));
            int jobCategory = Integer.parseInt(request
                    .getParameter("jobCategory"));
            String parameters = request.getParameter("parameters").trim();
            String cronExpression = request.getParameter("cronExpression")
                    .trim();
            int userId = 0;
            job.setUserId(userId);
            job.setJobName(jobName);
            job.setJobGroup(jobGroup);
            job.setJobClass("jobClass");
            job.setDescription(description);
            job.setPriority(priority);
            job.setJobCategory(jobCategory);
            job.setCommand(parameters);
            job.setCronExpression(cronExpression);
            jobBiz.addJob(job);
            return "redirect:/job/list.do?userId=0";
        }
        return "/job/add";
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String editJob(HttpServletRequest request,
            @RequestParam("jobId") int jobId, Model model) {
        JobModel job;
        String method = request.getMethod();
        if (method == RequestMethod.GET.name()) {
            job = jobBiz.getJobDetail(jobId);
            model.addAttribute("job", job);
        } else if (method == RequestMethod.POST.name()) {
            String priority = request.getParameter("priority");
            String jobCategory = request.getParameter("jobCategory");
            String description = request.getParameter("description").trim();
            String cronExpression = request.getParameter("cronExpression")
                    .trim();
            String parameters = request.getParameter("parameters").trim();
            Map<String, String> data = new HashMap<String, String>();
            data.put("priority", priority);
            data.put("jobCategory", jobCategory);
            data.put("description", description);
            data.put("cronExpression", cronExpression);
            data.put("parameters", parameters);
            jobBiz.editJob(data, jobId);
            return "redirect:/job/list";
        }
        return "/job/edit";
    }

}
