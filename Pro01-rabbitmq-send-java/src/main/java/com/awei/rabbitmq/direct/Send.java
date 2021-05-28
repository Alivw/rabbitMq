package com.awei.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 发送类
 * @Author: Awei
 * @Create: 2021-05-28 13:58
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

            channel.queueDeclare("myDirectQueue", true, false, false, null);
            /**
             * 声明一个交换机
             * 参数一： 为交换机名称，任意取值
             * 参数二： 为交换机类型 取值为： direct fanout topic headers
             * 参数三： 为是否为持久化交换机
             * 注意：
             *      1. 声明交换机时，如果这个交换机存在，则放弃声明 ，如果不存在，则声明交换机
             *      2. 这行代码是可有可无的，但是在使用前必须确保这个交换机必须存在
             */
            channel.exchangeDeclare("directExchange", "direct", true);
            /**
             * 将队列绑定到交换机
             * 参数一： 为队列的名称
             * 参数二： 为交换机名称
             * 参数三： 为消息的Routingkey 就是 bindingkey
             * 注意：
             *      1. 在进行队列和交换机绑定时 必须要确保交换机和队列已经成功地申明，否则会绑定失败
             */

            channel.queueBind("myDirectQueue", "directExchange", "directRoutingKey");
            String message = "direct测试消息";
            /**
             * 发送消息到指定队列
             * 参数一： 为交换机名称
             * 参数二： 为消息的RoutingKey 如果这个消息的RoutingKey 和某个队列与交换机绑定的RoutingKey一致，那么这个消息就会发送到指定的队列中
             * 注意：
             *      1. 发送消息时 必须确保交换机已经创建，并且确保已经正确的绑定到了某个队列
             */
            channel.basicPublish("directExchange", "directRoutingKey", null, message.getBytes(StandardCharsets.UTF_8));
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
