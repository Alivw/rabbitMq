package com.awei.rabbitmq.ask;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Description 手动应答模式
 * @Author jalivv
 * @Date 2021/12/30 13:23
 */
public class Task {
    public static final String QUEUE_NAME = "ask_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {
            String msg = in.next();
            /**
             * props: 消息持久化参数
             */
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息=====>" + msg);
        }
    }
}
