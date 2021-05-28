package com.awei;

import com.awei.service.Send;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SendApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(SendApplication.class, args);
        Send send = (Send) ac.getBean("send");
        send.send();

    }

}
