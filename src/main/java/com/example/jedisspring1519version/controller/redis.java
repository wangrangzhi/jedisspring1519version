package com.example.jedisspring1519version.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

@RestController
@Log
@RequestMapping("/sparkWebsite/api/v1/redis")
public class redis {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/save")
    public String trafficTotalrefashEverySenondsRedisRewrite(@RequestParam(value = "serveNameList") String serveNameList) throws ParseException, InvocationTargetException, IllegalAccessException {



        redisTemplate.opsForValue().set(serveNameList, serveNameList,60 * 60 * 6, TimeUnit.SECONDS);

        return "asv";

    }
}
