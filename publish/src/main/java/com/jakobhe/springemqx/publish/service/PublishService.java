package com.jakobhe.springemqx.publish.service;

import com.jakobhe.springemqx.publish.common.Context;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@MessagingGateway(defaultRequestChannel = Context.MQTT_PUBLISH_CHANNEL)
@Component
public interface PublishService {

    void sendToMqtt(String topic, String data);

    void sendToMqtt(String topic, String data, int qos);

    void sendToMqtt(String data);
}
