package com.awei.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 14:57
 **/
public class Send {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.113");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare("topicExchange", "topic", true);
            String message = "topic测试消息";
            channel.basicPublish("topicExchange", "aa", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
