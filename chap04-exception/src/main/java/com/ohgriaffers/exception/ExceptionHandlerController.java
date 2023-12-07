package com.ohgriaffers.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("controller-null")
    public String nullPointerExceptionTest(Model model){

        String str = null;
        System.out.println(str.charAt(0)); // exception 생겨서 다음으로 못넘어감

        return "/main";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e){
        System.out.println("controller 레벨의 Exception 처리");
        return "error/nullPointer";
    }

    @GetMapping("controller-user")
    public String userException() throws MemberRegistException {
        boolean check = true;
        if (check){
            throw new MemberRegistException("입사가 불가능 합니다.");
        }
        return "/";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String MemberRegistExceptionHandler(MemberRegistException e){
        System.out.println("controller 레벨의 Exception 처리");
        return "error/memberRegist";
    }

}
