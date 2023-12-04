package com.ohgiraffers.request;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {

        // port : 80 = web
        // port : 8080 = WAS
        // port : 443 = https

        // Get : localhost:8080/order/regist

    @GetMapping("/regist")
    public String registOrder(Model model){
        model.addAttribute("message","GET 방식의 주문 등록용 핸들러 메소드 호출");

        return "mappingResult";
    }

    @RequestMapping(value = {"modify","delete"},method = RequestMethod.POST)
    public String modifyAndDelete(Model model){

        model.addAttribute("message","post 방식의 주문정보 수정과 주문정보 삭제 공통 처리용 핸들러");
        return "mappingResult";
    }

    /* 3. path Variable
     * @Pathvariable 어노테이션을 이용해 요청을 path로부터 변수를 받아올 수 있다.
     * path variable로 전달 되는 {변수명} 값은 반드시 매개변수명과 동일해야 한다.
     * 만약 동일하지 않으면 @PathVariable("이름")을 설정해주어야 한다.
     * 이는 rest형 웹 서비스를 설계할 때 유용하게 사용된다.
     *
     * 인텔리제이의 builder설정을 Inteellij로 했을 경우에는 @PathVariable에 이름을 지정해주지 않으면 찾지 못한다.
     * */
    // 3 = orderNo

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo){
        model.addAttribute("message",orderNo+"번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @RequestMapping
    public String otherRequest(Model model){
        model.addAttribute("message","order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";
    }

}
