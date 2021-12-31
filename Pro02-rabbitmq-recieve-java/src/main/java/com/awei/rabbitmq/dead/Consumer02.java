package com.awei.rabbitmq.dead;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/31 16:25
 */
public class Consumer02 {

    // 死信队列
    public static final String DEAD_QUEUE = "dead_queue";


    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getChannel();
        DeliverCallback callback = (tag, msg) -> {
            System.out.println("接收到死信消息" + new String(msg.getBody()));
        };

        channel.basicConsume(DEAD_QUEUE, true,callback ,(msg)->{} );
    }
}
