package com.awei.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 21:11
 **/
@Configuration
public class AmqpConfig {


    @Bean
    public Queue queue() {
        return new Queue("myQueueDirect");
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("bootDirectExchange");
    }

    @Bean("binding")
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("bootRoutingKey").noargs();
    }
}
