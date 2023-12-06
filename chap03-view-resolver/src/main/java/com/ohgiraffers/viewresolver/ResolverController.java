package com.ohgiraffers.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/*")
public class ResolverController {

    @GetMapping("string")
    public String stringReturning(Model model){
        model.addAttribute("forwardMessage","문자열로 뷰 이름 반환함...");

        /**/

        return "result";
    }

    @GetMapping("string-redirect")
    public String stringRedirect(){

        /**/

        return "redirect:/";
    }

    @GetMapping("string-redirect-attr")
    public String stringRedirectFlashAttribute(RedirectAttributes rttr){

        // flash 영역이란?

        /**/

        rttr.addFlashAttribute("flashMessage1","redirect attr 사용하여 redirect..");
        return "redirect:/";
    }

    @GetMapping("modelandview")
    public ModelAndView modelAndView(ModelAndView modelAndView){
        modelAndView.addObject("forwardMessage","modelAndView를 이용한 모델과 뷰 반환");
        modelAndView.setViewName("result");
        return modelAndView;
    }

    @GetMapping("modelandview-redirect")
    public ModelAndView modelAndViewRedirect(ModelAndView mv){
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("modelandview-redirect-attr")
    public ModelAndView modelAndViewRedirect(ModelAndView mv, RedirectAttributes rttr){
       rttr.addFlashAttribute("flashMessage2","ModelAndView를 이용한 attr");
       mv.setViewName("redirect:/");
       return mv;
    }


}
