package com.awei.rabbitmq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: fanout交换机接收器
 * @Author: Awei
 * @Create: 2021-05-28 14:20
 **/
public class Recieve01 {
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
            /**
             * 由于fanout类型的交换机的消息，类须与广播的模式，他不需要绑定RoutingKey
             * 而 又可能会有很多个消费者来接受这个交换机中的数据，因此我们创建队列是要创建一个随机的队列名称
             * 没有参数的 queueDeclare 方法会创建一个名字为随机的一个队列
             * 这个队列的数据时 非持久化
             * 排外  ：同时最多只允许一个消费者监听当前队列
             * 自动删除的    ： 当没有任何消费者监听这个队列， 他就会被删除
             */
            String queueName = channel.queueDeclare().getQueue();
            channel.exchangeDeclare("fanoutExchange", "fanout", true);
            channel.queueBind(queueName, "fanoutExchange", "");
            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("消费者01接收到消息：" + message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
