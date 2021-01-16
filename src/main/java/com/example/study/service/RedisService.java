package com.example.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void testRedis() {
        redisTemplate.opsForValue().set("myKey", "myValue");
        redisTemplate.boundValueOps("name").set("csm");

        System.out.println(redisTemplate.boundValueOps("name").get());
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

    /**
     * 保存验证码
     * 设置过期时间5分钟
     *
     * @param code
     */
    public void saveCode(String phone, String code) {
        String phoneIdentity = "code_" + phone;//验证码标识
        redisTemplate.boundValueOps(phoneIdentity).set(code);
        redisTemplate.boundValueOps(phoneIdentity).expire(5, TimeUnit.MICROSECONDS);
    }

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    public String getCode(String phone) {
        String phoneIdentity = "code_" + phone;//验证码标识
        return redisTemplate.boundValueOps(phoneIdentity).get();
    }
}
