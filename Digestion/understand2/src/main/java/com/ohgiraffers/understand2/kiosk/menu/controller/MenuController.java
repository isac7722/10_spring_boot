package com.ohgiraffers.understand2.kiosk.menu.controller;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import com.ohgiraffers.understand2.kiosk.menu.exception.MenuException;
import com.ohgiraffers.understand2.kiosk.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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
    public ModelAndView searchAllMenu(ModelAndView modelAndView) {
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


    //auth
    @GetMapping("menu/authView")
    public String authView(){return "menu/views/authView";}

    @PostMapping("authentication")
    public ModelAndView authentication(ModelAndView modelAndView, MenuDTO authMenuDTO, HttpServletRequest request){

        System.out.println("authentication실행");

        System.out.println("-----------");
        System.out.println("menuService.searchMenuByName(authMenuDTO)");
        MenuDTO resultDTO = menuService.searchMenuByName(authMenuDTO);
        String message = "";



        if (Objects.isNull(resultDTO)){

            System.out.println("resultDTO없음");

            message = "인증된 사용자가 없습니다";
            modelAndView.addObject("result",message);
            modelAndView.setViewName("menu/result/Result");
            return modelAndView;
        }else {

            System.out.println("resultDTO있음");

            //인증된 경우 인증값을 추가

            request.getSession().setAttribute("authStatus", 1);

            System.out.println("authStatus 추가");

            message = "인증되었습니다.";
            modelAndView.addObject("result",message);
            modelAndView.setViewName("menu/result/Result");
            return modelAndView;
        }
    }

    @PostMapping("/logOut")
    public ModelAndView logOut(ModelAndView modelAndView, HttpServletRequest request){
        String message="";

        if (Objects.isNull(request.getSession().getAttribute("authStatus"))){
            message="인증되지 않은 사용자입니다";
            modelAndView.addObject("result",message);
            modelAndView.setViewName("menu/result/Result");
            return modelAndView;
        }else {
            if ((Integer)(request.getSession().getAttribute("authStatus")) == 1){
                request.getSession().setAttribute("authStatus",null);
                message="로그아웃 되었습니다.";
                modelAndView.addObject("result",message);
                modelAndView.setViewName("menu/result/Result");
                return modelAndView;

            }else{
                message = "인증정보가 잘못되었습니다.";
                request.getSession().setAttribute("authStatus",null);
                modelAndView.addObject("result",message);
                modelAndView.setViewName("menu/result/Result");
                return modelAndView;
            }
        }
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







    // Exceptions
    @ExceptionHandler(MenuException.class)
    public String MenuExceptionHandler(MenuException e, Model model){
        String message = "controller에서 menuException 처리함";

        System.out.println(message);
        System.out.println(e.getClass());

        model.addAttribute("message",message);
        model.addAttribute("message2",e.getClass());
        model.addAttribute("message3",e.getMessage());

        return "menu/views/error/errorView";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e, Model model){
        String message = "controller에서 nullPointerException 처리함";

        System.out.println(message);
        System.out.println(e.getClass());

        model.addAttribute("message",message);
        model.addAttribute("message2",e.getClass());
        model.addAttribute("message3",e.getMessage());

        return "menu/views/error/errorView";
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
        Double PLRatio = Double.parseDouble(tradingData.get("PLRatio"));
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

//    public boolean randomChanceGenerator(int chance){
//        if ((Math.random()*(chance-1))+1 <= (Math.random()*(100-1))+1){
//            return true;
//        }else {
//            return false;
//        }
//    }

    public boolean randomChanceGenerator(int chance){
        int randomChance = ThreadLocalRandom.current().nextInt(1,100);

        if (chance >= 100){
            return true;
        }else {
            if (randomChance <= chance){
                return true;
            }
        }
        return false;
    }


    public int randomChanceGenerator2(int chance, int totalCount){
        int result=0;

        for (int i = 0; i < totalCount; i++) {
            int randomChance = ThreadLocalRandom.current().nextInt(1,100);

            if (chance >= 100){
                result++;
            }else {
                if ( randomChance <= chance){
                    result++;
                }
            }
        }

        return result;
    }



//    대기중 프로젝트


    @PostMapping("/randomChanceTester")
    public ModelAndView randomChanceTester(ModelAndView modelAndView, @RequestParam Map<String,String> randomChanceData){
        int chance = Integer.parseInt(randomChanceData.get("chance"));
        int totalCount = Integer.parseInt(randomChanceData.get("number"));
        int profit = randomChanceGenerator2(chance,totalCount);
        System.out.println(profit);

        int winRate =  (int)(((double)profit / (double)totalCount) * 100);
        System.out.println(winRate);

        String message ="총"+totalCount+"회 돌린 결과: 예상 확률은 "+chance+"이고, 실제 확률은 "+winRate+"입니다.";

        modelAndView.addObject("message",message);
        modelAndView.setViewName("menu/views/randomChanceResult");
        return modelAndView;
    }



}
