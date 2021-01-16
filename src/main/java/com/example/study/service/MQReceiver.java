package com.example.study.service;

import com.example.study.util.SendSms;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "queue.test")
public class MQReceiver {
    @Autowired
    private SendSms sendSms;

    @RabbitHandler
    public void process(String message) {
        //解析json
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(message, Map.class);
        //得到phone和code
        String code = map.get("code");
        String phone = map.get("phone");
        //调用阿里云通讯
        sendSms.sendCode(phone,code);
    }

}
