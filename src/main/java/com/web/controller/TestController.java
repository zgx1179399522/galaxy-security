package com.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/30
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/admin")
    public String admin(){
        return "admin";
    }
}
