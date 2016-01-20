package com.test.quartz.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class MenuInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("userId", 0);
        Map<String, String> urlList = new HashMap<String, String>();
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("userId", request.getSession().getAttribute("userId")
                .toString());
        urlList.put(
                "urlJobList",
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/job/list").buildAndExpand(uriVariables)
                        .toUriString());
        urlList.put("urlJobAdd", ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/job/add").build()
                .toUriString());
        request.setAttribute("urlList", urlList);
        return true;
    }

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
