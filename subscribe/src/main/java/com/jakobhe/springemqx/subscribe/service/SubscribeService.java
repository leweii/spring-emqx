package com.jakobhe.springemqx.subscribe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    @Bean
    public MessageHandler messageHandler() {
        return message -> {
            System.out.println("===================");
            System.out.println(message.getHeaders());
            System.out.println(message.getPayload());
            System.out.println("===================");
        };
    }
}
