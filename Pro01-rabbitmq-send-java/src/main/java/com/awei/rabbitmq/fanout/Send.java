package com.awei.rabbitmq.fanout;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 发送类
 * @Author: Awei
 * @Create: 2021-05-28 13:58
 **/
public class Send {

    public static final String EXCHANGE_NAME = "fanout";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getChannel();

        Connection connection = null;

        try {
            /**
             * 由于使用Fanout类型的交换机，因此消息的接收方可能会有多个，因此不建议在消息发送时来创建队列
             * 以及绑定交换机，建议在消费者中 创建队列并绑定交换机
             * 但是发送消息时，至少应该确保 交换机时存在的
             */

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
            String message = "fanout测试消息";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
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
