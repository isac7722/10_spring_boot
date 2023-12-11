package com.ohgiraffers.securitysession.user.controller;

import com.ohgiraffers.securitysession.user.model.dto.SignupDTO;
import com.ohgiraffers.securitysession.user.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    private MemberService memberService;

    @GetMapping("/signup")
    public void signup(){

    }



    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute SignupDTO signupDTO, ModelAndView modelAndView){
        //유효성 검사 로직 추가

        int result = memberService.regist(signupDTO);

        String message;

        if (result>0) {
            message = "회원가입이 완료되었습니다";
            modelAndView.setViewName("auth/login");
        }else {
            message="회원가입이 실패되었습니다.";
            modelAndView.setViewName("user/signup");
        }
        modelAndView.addObject("message",message);


        return modelAndView;
    }
}
