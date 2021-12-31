package com.awei.rabbitmq.dead;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/31 10:36
 */
public class Consumer01 {

    // 普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    // 死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";


    // 普通队列
    public static final String NORMAL_QUEUE = "normal_queue";

    // 死信队列
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {


        Channel channel = MqUtils.getChannel();


        // 声明普通交换机以及死信交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);




        /**
         * 参数
         * 1、指定过期时间
         * 2、正常队列设置死信交换机
         * 3、设置死信 Routingkey
         */

        Map<String, Object> arguments = new HashMap<>();
        //arguments.put("x-message-ttl", 10 * 1000);    // 通常由消息发送方去指定，比较灵活，不然在此处写死，死信队列中的ttl均为10s
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");

        // 声明普通队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);


        // 绑定交换机于队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        // 接收成功回调
        DeliverCallback callback = (tag, msg) -> {
            System.out.println(new String(msg.getBody(), "utf-8"));
        };

        System.out.println("C1等待接收消息.....");

        // 接收消息
        channel.basicConsume(NORMAL_QUEUE, true, callback, (msg) -> {
        });



    }
}
