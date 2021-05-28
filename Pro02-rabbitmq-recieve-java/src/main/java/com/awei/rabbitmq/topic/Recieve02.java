package com.awei.rabbitmq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 14:56
 **/
public class Recieve02 {
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
            channel.queueDeclare("topicQueue02", true, false, false, null);
            channel.exchangeDeclare("topicExchange", "topic", true);
            channel.queueBind("topicQueue02", "topicExchange", "aa.*");
            channel.basicConsume("topicQueue02", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("消费者01,RoutingKey=aa.* ,接收到消息" + message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
