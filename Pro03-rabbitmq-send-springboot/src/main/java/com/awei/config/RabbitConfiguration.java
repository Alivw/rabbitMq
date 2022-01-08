package com.awei.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description rabbitmq 声明组建
 * @Author jalivv
 * @Date 2022/1/7 16:42
 */

@Configuration
public class RabbitConfiguration {

    // 交换机
    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";

    // 死信队列 routingkey
    public static final String ORDER_CREATE_ORDER = "order.create.order";

    // 延时队列 routingkey
    public static final String ORDER_RELEASE_ORDER = "order.release.order";

    // 死信队列名
    public static final String ORDER_DELAY_QUEUE_NAME = "order.delay.queue";

    // 延时队列名
    public static final String ORDER_RELEASE_QUEUE_NAME = "order.release.queue";


    @Bean("delayExchange")
    public Exchange delayExchange() {

        return new TopicExchange(ORDER_EVENT_EXCHANGE, true, false, null);
    }


    @Bean("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_EVENT_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", ORDER_RELEASE_ORDER);
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return new Queue(ORDER_DELAY_QUEUE_NAME, true, false, false, args);
    }

    @Bean
    public Queue releaseQueue() {

        return new Queue(ORDER_RELEASE_QUEUE_NAME, true, false, false);
    }


    @Bean
    public Binding delayBinding() {
        return new Binding(ORDER_DELAY_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                ORDER_EVENT_EXCHANGE,
                ORDER_CREATE_ORDER,
                null
        );
    }

    @Bean
    public Binding releaseBind(){
        return new Binding(ORDER_RELEASE_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                ORDER_EVENT_EXCHANGE,
                ORDER_RELEASE_ORDER,
                null
        );
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
