package com.ohgiraffers.securitysession.auth.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/fail")
    public ModelAndView loginFail(@RequestParam String message, ModelAndView modelAndView){
        modelAndView.addObject("message",message);
        modelAndView.setViewName("/auth/fail");
        return modelAndView;
    }
}
