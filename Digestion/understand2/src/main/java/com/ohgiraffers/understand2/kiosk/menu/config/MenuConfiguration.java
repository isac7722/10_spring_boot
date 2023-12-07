package com.ohgiraffers.understand2.kiosk.menu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MenuConfiguration implements WebMvcConfigurer {

    @Autowired
    private MenuIntercepter menuIntercepter;


    // 전체조회 기능 외 모든 기능에 인터셉터 ㄱㄱ
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuIntercepter)
                .addPathPatterns("/menu/registView")
                .addPathPatterns("/menu/searchView")
                .addPathPatterns("/menu/deleteView");


    }
}
