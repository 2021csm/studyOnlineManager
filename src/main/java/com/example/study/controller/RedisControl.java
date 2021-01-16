package com.example.study.controller;

import com.example.study.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisControl {
    @Autowired
    private RedisService redisService;
    @GetMapping("/testRedis")
    public void test(){
        redisService.testRedis();
    }
}
