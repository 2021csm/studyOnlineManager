package com.example.study.util;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQconfig {
    @Bean
    public Queue queueTest() {
        return new Queue("queue.test");
    }
    @Bean
    public Queue queueTest1() {
        return new Queue("queue.test1");
    }
    @Bean
    public Queue queueTest2() {
        return new Queue("queue.test2");
    }
    @Bean
    public Exchange exchange_fanout() {
        return  new FanoutExchange("fanout_test");
    }
    @Bean
    public Binding bindingExchange_1() {
        return new Binding("queue.test1",
                Binding.DestinationType.QUEUE,"fanout_test","",null);

    }
    @Bean
    public Binding bindingExchange_2() {
        return new Binding("queue.test2",
                Binding.DestinationType.QUEUE,"fanout_test","",null);

    }


}
