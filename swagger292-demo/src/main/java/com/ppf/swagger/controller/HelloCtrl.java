package com.ppf.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCtrl {

    @GetMapping("/hello")
    public String test0() {
        return "hello world";
    }
}
