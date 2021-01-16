package com.example.study.service;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MQSender {
   @Autowired
   private AmqpTemplate amqpTemplate;
   //直接模式
   public void testSendMessage(){
       amqpTemplate.convertAndSend("","queue.test","hello world");
   }
   //分列模式
    public void testSendMessageByExchange(){
        amqpTemplate.convertAndSend("exchange.fanout_test","queue.test","分列模式");
    }
   public void SendPhoneCode(Map<String,String> map){
       if(map!=null&&map.size()!=0) {
           amqpTemplate.convertAndSend("", "queue.test", JSONUtils.toJSONString(map));
       }
   }
}


