package com.awei.rabbitmq.ask;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/30 13:30
 */
public class Work1 {
    public static final String QUEUE_NAME = "ask_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getChannel();

        DeliverCallback callback = (tag, msg) -> {
            String string = new String(msg.getBody());
            System.out.println("接收到消息，正在处理.....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("消息处理完毕====>" + string);

        };

        channel.basicConsume(QUEUE_NAME, false, callback, (msg) -> {

        });

    }
}
