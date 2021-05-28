package com.awei.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-28 20:40
 **/
@Configuration
public class AmqpConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("bootDirectExchange");
    }
}
