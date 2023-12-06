package com.ohgiraffers.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"/","/main"})
    public String main(){return "main";}

    // localhost:8080 이것은 root요청 = "/"

    //resource에서 static은 사진과 같은 정적인 것을 관리하는 공간
}
