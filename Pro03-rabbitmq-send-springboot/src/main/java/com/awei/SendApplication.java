package com.awei;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class SendApplication {


    public static void main(String[] args) {
        SpringApplication.run(SendApplication.class, args);
    }




    }
