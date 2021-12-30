package com.awei.rabbitmq.confirm;

import com.awei.rabbitmq.util.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Description TODO
 * @Author jalivv
 * @Date 2021/12/30 17:29
 */
public class Task {
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception{
        //publishInvidully();
        //publishMulty();

        publishConfirmAsync();
    }


    public static void publishInvidully() throws Exception{
        Channel channel = MqUtils.getChannel();
        channel.confirmSelect();
        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, false, false, true, null);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            channel.basicPublish("", queue, null, new String(i + "").getBytes(StandardCharsets.UTF_8));
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            }



        }




        System.out.println(System.currentTimeMillis() - begin);



    }




    public static void publishMulty() throws Exception{
        Channel channel = MqUtils.getChannel();
        channel.confirmSelect();
        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, false, false, true, null);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            channel.basicPublish("", queue, null, new String(i + "").getBytes(StandardCharsets.UTF_8));
            if (i / 99 == 0) {
                if (channel.waitForConfirms()) {
                    System.out.println("消息发送成功");
                }
            }




        }




        System.out.println(System.currentTimeMillis() - begin);



    }



    public static void publishConfirmAsync() throws Exception{

        ConcurrentSkipListMap<Long, String> map = new ConcurrentSkipListMap<>();

        Channel channel = MqUtils.getChannel();
        channel.confirmSelect();
        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, false, false, true, null);

        //   消息发送成功回调
        ConfirmCallback successCalback = (deliveryTag, multiple) -> {
            if (multiple) {
                // 批量删除
                ConcurrentNavigableMap<Long, String> confirmed = map.headMap(deliveryTag);
                confirmed.clear();
            }else{
                map.remove(deliveryTag);
            }
            System.out.println("确认的消息" + deliveryTag);
        };

        //  消息发送失败回调
        ConfirmCallback failCalback = (deliveryTag, multiple) -> {

            System.out.println("未确认的消息：" + deliveryTag);
        };

        /**
         * 准备消息的监听器，监听哪些消息成功，哪些消息失败
         */
        channel.addConfirmListener(successCalback, failCalback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            channel.basicPublish("", queue, null, new String(i + "").getBytes(StandardCharsets.UTF_8));
            map.put(channel.getNextPublishSeqNo(), i + "");
        }
        System.out.println("异步确认耗时："+(System.currentTimeMillis() - begin));

    }




}
