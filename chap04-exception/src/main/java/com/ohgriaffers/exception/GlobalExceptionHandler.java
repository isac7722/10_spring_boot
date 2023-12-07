package com.ohgriaffers.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Exception 나오면 여기로 보냄

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e){
        System.out.println("global 레벨의 Exception 처리");
        return "error/nullPointer";
    }


    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException e){
        System.out.println("Global레벨의 exception 처리");
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }


    @ExceptionHandler(Exception.class)
    public String nullPointerExceptionHandler(Exception e){
        System.out.println("exception 발생함");
        return "error/default";
    }

}
