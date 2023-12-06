package com.ohgiraffers.understand2.kiosk.menu.controller;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import com.ohgiraffers.understand2.kiosk.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/menu/*")
@SessionAttributes("id")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/redirect")
    public String redirect(){
        return "redirect:/";
    }

    // Related to DB
    @GetMapping("/searchAllMenu")
    public ModelAndView searchAllMenu(ModelAndView modelAndView){
        System.out.println("test");

        List<MenuDTO> menuList = menuService.searchAllMenu();

        if (Objects.isNull(menuList)){
            System.out.println("menuService에서 값을 전달받지 못함");
        }
        modelAndView.addObject("menuList",menuList);
        modelAndView.setViewName("menu/result/allMenuList");
        return modelAndView;
    }

    @GetMapping("/searchMenuByName")
    public ModelAndView searchMenuByName(ModelAndView modelAndView, MenuDTO menuDTO){

        MenuDTO searchResultMenuDTO = menuService.searchMenuByName(menuDTO);

        if (Objects.isNull(searchResultMenuDTO)){
            modelAndView.setViewName("menu/result/noResult");
            return modelAndView;
        }else {
            modelAndView.addObject("menu",searchResultMenuDTO);
            modelAndView.setViewName("menu/result/searchResult");
            return modelAndView;
        }
    }

    @PostMapping("/registMenu")
    public ModelAndView registMenu(ModelAndView modelAndView, MenuDTO newMenu){

        int registResult = menuService.registMenu(newMenu);
        String result ="";

        if (registResult == 0){
            result ="등록 실패했습니다";
        }else {
            result ="등록 성공했습니다";
        }

        modelAndView.setViewName("menu/result/Result");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    @GetMapping("/deleteMenuByName")
    public ModelAndView deleteMenuByName(ModelAndView modelAndView, MenuDTO menuDTO){

        int deleteResult = menuService.deleteMenu(menuDTO);
        String result = "";

        if (deleteResult == 1 ){
            result="성공적으로 삭제되었습니다!";
        }else {
            result="삭제하는데 실패하였습니다..";
        }

        modelAndView.addObject("result",result);
        modelAndView.setViewName("menu/result/Result");
        return modelAndView;
    }


    // Views
    @GetMapping("menu/searchView")
    public ModelAndView searchView(ModelAndView modelAndView){
        modelAndView.setViewName("menu/views/searchView");
        return modelAndView;
    }
    @GetMapping("menu/registView")
    public String registView(){
        return "menu/views/registView";
    }
    @GetMapping("menu/deleteView")
    public String deleteView(){
        return "menu/views/deleteView";
    }

}
