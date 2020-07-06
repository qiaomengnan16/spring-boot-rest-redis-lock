package com.demo.spring.redis.lock.rest;

import com.demo.spring.redis.lock.aop.ConcurrentLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloRest {

    @ConcurrentLock
    @GetMapping("/lock")
    public String hello(String userId) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello" + userId;
    }

}
