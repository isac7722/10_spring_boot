package com.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**/

@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    private final MenuService menuService;

    /* 전처리 메소드 */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getParameter("auth").equals("admin")) {
            response.sendRedirect("/");
            return false;
        }

        System.out.println("prehandler 호출함");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);

        //컨트롤러를 이어서 호출한다. false인 경우 핸들러 메소드를 호출하지 않는다.
        return true;
    }


    //후 처리 메소드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler 호출함");
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();

        modelAndView.addObject("interval",endTime - startTime);
    } // 이후 modelAndView에 view에 result 값이 담겨서 result로 ㄱㄱ / interval로 같이 가져가

    @Override // Hand
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after Complete 호출함");
        menuService.method();
    }
}
