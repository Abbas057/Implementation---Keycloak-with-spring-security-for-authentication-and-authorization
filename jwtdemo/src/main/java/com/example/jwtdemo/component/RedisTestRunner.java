//package com.example.jwtdemo.component;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RedisTestRunner implements CommandLineRunner {
//
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @Override
//    public void run(String... args) {
//
//        redisTemplate.opsForValue().set("hello", "Redis Working");
//
//        String value = redisTemplate.opsForValue().get("hello");
//
//        System.out.println("Redis Value : " + value);
//    }
//}