package com.example.rateLimiting.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {

    @GetMapping("/getinfo")
    public String getInfo(@RequestHeader String id){
        System.out.println(" id " + id);
        return "HI i am deepak";
    }
}
