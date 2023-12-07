package com.ohgiraffers.understand2.kiosk.menu.controller;

import com.ohgiraffers.understand2.kiosk.menu.exception.MenuException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
@ComponentScan(basePackages = "ohgiraffers.understand2.kiosk")
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e, Model model){
        String message = "Global에서 nullPointerException 처리함";

        return showExceptionView(model,e,message);
    }

    @ExceptionHandler(Exception.class)
    public String userExceptionHandler(Exception e, Model model){
        String message = "Global에서 nullPointerException 처리함";

        return showExceptionView(model,e,message);
    }

    @ExceptionHandler(MenuException.class)
    public String menuExceptionHandler(MenuException e, Model model){
        String message = "Global에서 MenuException 처리함";

        return showExceptionView(model,e,message);
    }



    public String showExceptionView(Model model, Exception e, String message){
        System.out.println(message);
        System.out.println(e.getClass());

        model.addAttribute("message",message);
        model.addAttribute("message2",e.getClass());
        model.addAttribute("message3",e.getMessage());

        return "menu/views/error/errorView";
    }


}
