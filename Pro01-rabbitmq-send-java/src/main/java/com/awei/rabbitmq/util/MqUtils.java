package com.awei.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/30 10:09
 */
public class MqUtils {

    public static Channel getChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("47.96.76.101");
        factory.setUsername("admin");
        factory.setPassword("Szw159421");

        return factory.newConnection().createChannel();
    }
}
