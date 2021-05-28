package com.awei.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 20:33
 **/
@Service
public class Send {


    @Resource
    private AmqpTemplate template;

    public void send() {
        template.convertAndSend("bootDirectExchange", "bootRoutingKey", "SpringbootDireck发送的消息");
    }


}
