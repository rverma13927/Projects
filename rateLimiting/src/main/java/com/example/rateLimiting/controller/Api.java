package com.example.rateLimiting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Api {

    @GetMapping("/getinfo")
    public String getInfo(){
        return "HI i am deepak";
    }
}
