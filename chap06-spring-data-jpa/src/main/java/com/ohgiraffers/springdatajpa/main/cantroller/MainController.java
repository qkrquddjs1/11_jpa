package com.ohgiraffers.springdatajpa.main.cantroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 스프링 컨트롤러를 설정
public class MainController {


    @GetMapping(value = {"/", "/main"}) //GET메소드로 요청을 처리하는 핸들러 정의 요청경로는 "/" 또는 "/main"입니다.
    public String main() {// main 메소드를 정의
        return "main/main";} // "main/main" 을 반환 view 뷰에서 화몀 반환
}
