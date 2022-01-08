package com.awei.listener;

import com.awei.config.RabbitConfiguration;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Description 消息监听器
 * @Author jalivv
 * @Date 2022/1/7 18:49
 */
@RabbitListener(queues = {RabbitConfiguration.ORDER_RELEASE_QUEUE_NAME})
@Service
public class OrderListener {


    @RabbitHandler
    public void msgHandler(Message msg,String msgStr, Channel channel) {
        System.out.println("接收到消息===》"+msg.getBody());
        System.out.println(msgStr);

    }
}

