package com.awei.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 21:09
 **/
@Service
public class Recieve {

    @RabbitListener(queues = "myQueueDirect")
    public void recieve(String message) {
        System.out.println("boot的direct消息====" + message);
    }
}
