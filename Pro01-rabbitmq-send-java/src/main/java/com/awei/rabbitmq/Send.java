package com.awei.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Description: 消息发送
 * @Author: Awei
 * @Create: 2021-05-28 11:08
 **/
public class Send {


    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        //创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        /**
         * 配置rabbitmq 的连接信息
         */
        factory.setHost("47.96.76.101");   //指定ip
        factory.setPort(5672);      //指定端口号
        factory.setUsername("admin");       // 指定账号
        factory.setPassword("Szw159421");       // 指定密码

        Connection connection = null;       //定义连接
        Channel channel = null;             // 定义通道

        try {
            connection = factory.newConnection();    // 获取连接
            channel = connection.createChannel();    // 获取通道
            /**
             * 声明一个队列
             * 第一个参数： 队列名 任意取值
             * 第二个参数： 是否为持久化队列
             * 第三个参数： 是否排外 如果排外，则这个队列只允许一个消费者监听
             * 第四个参数： 是否自动删除 如果为true 则表示当队列中没有消息，也没有消费者连接时，就会自动删除这个队列
             * 第五个参数： 为队列的一些属性设置通常为 null 即可
             * 注意：
             *      1. 声明队列时，这个队列名称如果已经存在，则放弃声明，如果队列不存在，则会声明一个新的队列
             *      2. 队列名可以取值任意 但是要与消息接受时 完全一致
             *      3. 这行代码是可有可无的 但是一定要在发送消息前确认队列名称已经存在 在rabbitmq 中，否则就会出现问题
             */
            channel.queueDeclare(QUEUE_NAME, true, false, true, null);

            String message = "我的RabbitMq的测试消息11";
            /**
             * 发送消息到MQ
             * 参数一： 为交换机名称，这里为空字符串，表示不使用交换机
             * 参数二： 为队列名 或RoutingKey，当制定了交换机名称以后，这个值就是RoutingKey
             * 参数三： 为消息属性信息 通常为空即可
             * 参数四： 为具体的消息数据的字节数组
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
            System.out.println("消息发送成功");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Exception e) {
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
