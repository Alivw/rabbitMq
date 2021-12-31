package com.awei.rabbitmq.dead;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/31 11:05
 */
public class Send {

    public static final String NORMAL_EXCHANGE = "normal_exchange";


    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getChannel();

        //  死信消息，设置ttl 时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().expiration("10000").build();


        for (int i = 0; i < 10; i++) {
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, new String("死信队列msg" + i).getBytes(StandardCharsets.UTF_8));
        }

    }
}
