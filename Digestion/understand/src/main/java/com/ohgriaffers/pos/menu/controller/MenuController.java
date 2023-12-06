package com.ohgriaffers.pos.menu.controller;


import com.ohgriaffers.pos.menu.dto.MenuDTO;
import com.ohgriaffers.pos.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/menu/*")
@SessionAttributes("id")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/searchAllMenu")
    public ModelAndView selectAllMenu(ModelAndView modelAndView){

        List<MenuDTO> menus = menuService.selectAllMenu();

        if (Objects.isNull(menus)){
            System.out.println("exception으로 대체한다.");
        }
        modelAndView.addObject("menus",menus);
        modelAndView.setViewName("menu/searchAll");

        return modelAndView;
    }

    @GetMapping ("/search")
    public void allMenu(){}


    // 요청 방식에 따라서 다르다
    @GetMapping ("searchResult")
    public ModelAndView selectMenu(ModelAndView modelAndView, MenuDTO menuDTO){

        List<MenuDTO> menu = new ArrayList<>();
          menu.add(menuService.selectMenu(menuDTO));


        if (Objects.isNull(menu)){
            System.out.println("메뉴서비스에서 에러");
        }

        modelAndView.addObject("menu",menu);
        modelAndView.setViewName("menu/searchResult");

        return modelAndView;
    }

    @PostMapping("/regist")
    public ModelAndView regist(ModelAndView modelAndView,@RequestParam Map<String, String> parameters){

        MenuDTO newMenu = new MenuDTO();

        newMenu.setName(parameters.get("name"));
        newMenu.setPrice(Integer.parseInt(parameters.get("price")));
        newMenu.setCategoryCode(Integer.parseInt(parameters.get("categoryCode")));
        newMenu.setStatus(parameters.get("status"));

        menuService.registMenu(newMenu);

        modelAndView.setViewName("menu/registResult");
        return modelAndView;
    }


}
