package com.awei.controller;

import com.awei.config.RabbitConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author jalivv
 * @Date 2022/1/7 19:26
 */
@RestController
public class HelloController {

    @Autowired
    RabbitTemplate rabbitTemplate;



    @GetMapping("/hello")
    public String hello() {
        rabbitTemplate.convertAndSend(RabbitConfiguration.ORDER_EVENT_EXCHANGE,
                RabbitConfiguration.ORDER_CREATE_ORDER,
                "hello gaga");
        return "hello";
    }
}
