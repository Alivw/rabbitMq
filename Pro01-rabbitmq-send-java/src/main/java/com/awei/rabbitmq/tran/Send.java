package com.awei.rabbitmq.tran;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 19:46
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

            channel.queueDeclare("transactionQueue", true, false, false, null);
            channel.exchangeDeclare("tranExchange", "direct", true);

            channel.queueBind("transactionQueue", "tranExchange", "tranQueueRoutingKey");
            String message = "direct测试消息";
            channel.txSelect();
            channel.basicPublish("tranExchange", "tranQueueRoutingKey", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(10 / 0);
            channel.basicPublish("tranExchange", "tranQueueRoutingKey", null, message.getBytes(StandardCharsets.UTF_8));
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel != null) {
                try {
                    channel.txRollback();
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
