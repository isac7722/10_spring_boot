package com.ohgiraffers.understand2.kiosk.menu.controller;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import com.ohgiraffers.understand2.kiosk.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
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
        String result = resultMessage(registResult,"regist");

        modelAndView.setViewName("menu/result/Result");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    @GetMapping("/deleteMenuByName")
    public ModelAndView deleteMenuByName(ModelAndView modelAndView, MenuDTO menuDTO){

        int deleteResult = menuService.deleteMenu(menuDTO);
        String result = resultMessage(deleteResult,"delete");

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



    //Result 반환 메소드
    public String resultMessage(int result, String method){


        if (result == 1){ //성공
            if(method.equals("regist")){
                return "등록 성공했습니다";
            }else if (method.equals("delete")){
                return "성공적으로 삭제되었습니다!";
            }
        } else { //실패
            if(method.equals("regist")){
                return "등록 실패했습니다";
            }else if (method.equals("delete")){
                return "삭제하는데 실패하였습니다..";
            }
        }
        return "알수 없는 에러가 발생했습니다. \n이전 페이지로 돌아가주세요..";
    }

    //virtualTradingSimulator
    @GetMapping("menu/virtualTradingSimulator")
    public String virtualTradingSimulator(){
        return "menu/views/virtualTradingSimulator";
    }

    @GetMapping("/virtualTradingLogic")
    public ModelAndView virtualTradingLogic(ModelAndView modelAndView, @RequestParam Map<String,String> tradingData){
        int seedMoney = Integer.parseInt(tradingData.get("seedMoney"));
        Double risk = Double.parseDouble(tradingData.get("risk")) / 100; // risk is self-percentaged
        int PLRatio = Integer.parseInt(tradingData.get("PLRatio"));
        int chance = Integer.parseInt(tradingData.get("chance"));
        int tradingDays = Integer.parseInt(tradingData.get("tradingDays"));
        int wealth = seedMoney;
        int max = seedMoney;
        int min = seedMoney;

        String result ="";

        for (int i = 1; i <= tradingDays; i++) {
            System.out.println(wealth);
            if (randomChanceGenerator(chance)){
                wealth += (int)((risk*PLRatio)*wealth);
            }else {
                wealth -= (int) (risk*wealth);
                if (wealth <= 0){
                    result = i+"일차에 승천하셨습니다             시작금: "+seedMoney+"원             최종 재산은 "+wealth+"원              " +
                    "최대재산은 "+max+"원             " +
                            "최소재산은 "+min+"원";
                    modelAndView.addObject("result",result);
                    modelAndView.setViewName("menu/result/Result");
                    return modelAndView;
                }
            }
            if (wealth < min){
                min = wealth;
            }
            if (wealth > max){
                max = wealth;
            }
        }
        result = tradingDays+"일동안 투자한 결과:               시작금: "+seedMoney+"원             최종 재산은 "+wealth+"원              " +
                "최대재산은 "+max+"원             " +
                "최소재산은 "+min+"원";
        modelAndView.addObject("result",result);
        modelAndView.setViewName("menu/result/Result");
        return modelAndView;

    }

    public boolean randomChanceGenerator(int chance){
        if ((Math.random()*(chance-1))+1 >= (Math.random()*(100-1))+1){
            return true;
        }else {
            return false;
        }





    }

}
