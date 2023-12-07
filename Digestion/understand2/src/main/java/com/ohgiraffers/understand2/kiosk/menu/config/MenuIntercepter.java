package com.ohgiraffers.understand2.kiosk.menu.config;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import com.ohgiraffers.understand2.kiosk.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class MenuIntercepter implements HandlerInterceptor {

    public MenuIntercepter(MenuService menuService) {
        this.menuService = menuService;
    }

    private final MenuService menuService;


    // pre
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle실행");
        //먼저 auth 값이 있는지 확인 없으면 반환
        if (Objects.isNull(request.getSession().getAttribute("authStatus"))){
            System.out.println("authStatus 값이 없음");
            response.sendRedirect("/menu/authView");
            return false;
        }else {
            if ((Integer)(request.getSession().getAttribute("authStatus")) == 1){
                System.out.println("authStatus 값 있다");
                return true;
            }else {
                response.sendRedirect("/menu/authView");
                return false;
            }
        }


//        List<MenuDTO> menuList = menuService.searchAllMenu();
//
//        // Iterator를 활용해서 List 안에 있는 값 하나씩 비교
//        Iterator<MenuDTO> iterator = menuList.iterator();
//        while (iterator.hasNext()){
//            MenuDTO authMember = iterator.next();
//            if (request.getParameter("auth").equals(authMember.getName())){
//                return true;
//            }
//        }
//        response.sendRedirect("/menu/authView");
//        return false;

    }

    // post
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle실행");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // after post
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterComplete실행");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
