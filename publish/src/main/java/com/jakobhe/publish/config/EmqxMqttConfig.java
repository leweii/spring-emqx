package com.jakobhe.publish.config;

import com.jakobhe.publish.common.Context;
import com.jakobhe.publish.properties.EmqxMqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

@Configuration
@IntegrationComponentScan
public class EmqxMqttConfig {

    @Resource
    private EmqxMqttProperties emqxMqttProperties;

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(emqxMqttProperties.getUsername());
        connectOptions.setPassword(emqxMqttProperties.getPassword().toCharArray());
        connectOptions.setServerURIs(new String[]{emqxMqttProperties.getHostUrl()});
        connectOptions.setKeepAliveInterval(emqxMqttProperties.getKeepAlive());
        connectOptions.setConnectionTimeout(emqxMqttProperties.getTimeout());
        connectOptions.setMqttVersion(4);
        connectOptions.setCleanSession(true);

        String playload = "disconnect";
        connectOptions.setWill("jakobtopic", playload.getBytes(), emqxMqttProperties.getQos(), false);

        return connectOptions;
    }

    @Bean
    public MqttPahoClientFactory getMqttPahoPublishClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean(name = Context.MQTT_PUBLISH_CHANNEL)
    public MessageChannel  getMqttPublishMessageChannel(){
        DirectChannel directChannel = new DirectChannel();
        return  directChannel;
    }

    @Bean
    @ServiceActivator(inputChannel = Context.MQTT_PUBLISH_CHANNEL)
    public MessageHandler publish() {
        MqttPahoMessageHandler mqttPahoMessageHandler =
                new MqttPahoMessageHandler(emqxMqttProperties.getClientId(), getMqttPahoPublishClientFactory());
        mqttPahoMessageHandler.setDefaultQos(emqxMqttProperties.getQos());
        mqttPahoMessageHandler.setDefaultTopic(emqxMqttProperties.getDefaultTopic());
        return mqttPahoMessageHandler;
    }
}
