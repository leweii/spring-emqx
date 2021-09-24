package com.jakobhe.springemqx.subscribe.service;

import com.jakobhe.springemqx.subscribe.common.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    @Bean
    @ServiceActivator(inputChannel = Context.MQTT_SUBSCRIBE_CHANNEL)
    public MessageHandler messageHandler() {
        return message -> {
            System.out.println("===================");
            System.out.println(message.getHeaders());
            System.out.println(message.getPayload());
            System.out.println("===================");
        };
    }
}
