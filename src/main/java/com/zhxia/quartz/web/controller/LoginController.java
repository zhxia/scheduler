package com.zhxia.quartz.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class LoginController {
    @RequestMapping(value="/login",method={RequestMethod.GET,RequestMethod.POST})
    public String login(HttpServletRequest request,HttpServletResponse response){
        return "/admin/login";
    }
}
