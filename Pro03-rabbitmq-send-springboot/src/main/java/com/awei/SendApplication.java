package com.awei;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableRabbit
public class SendApplication {


    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(SendApplication.class, args);


    }

}
