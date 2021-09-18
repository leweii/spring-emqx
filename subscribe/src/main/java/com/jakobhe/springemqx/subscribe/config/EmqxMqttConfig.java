package com.jakobhe.springemqx.subscribe.config;

import com.jakobhe.springemqx.subscribe.common.Context;
import com.jakobhe.springemqx.subscribe.properties.EmqxMqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

@Configuration
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
        connectOptions.setCleanSession(true);

        String playload = "disconnect";
        connectOptions.setWill("jakobtopic", playload.getBytes(), emqxMqttProperties.getQos(), false);

        return connectOptions;
    }

    @Bean(name = Context.MQTT_PUBLISH_CHANNEL)
    public MqttPahoClientFactory getMqttPahoPublishClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean(name = Context.MQTT_SUBSCRIBE_CHANNEL)
    public MqttPahoClientFactory getMqttPahoSubscribeClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean
    public MessageChannel getMqttMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = Context.MQTT_SUBSCRIBE_CHANNEL)
    public MessageProducer subscribe() {
        MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter =
                new MqttPahoMessageDrivenChannelAdapter(emqxMqttProperties.getClientId(), getMqttPahoSubscribeClientFactory(), emqxMqttProperties.getDefaultTopic().split(","));
        mqttPahoMessageDrivenChannelAdapter.setCompletionTimeout(emqxMqttProperties.getTimeout());
        mqttPahoMessageDrivenChannelAdapter.setConverter(new DefaultPahoMessageConverter());
        mqttPahoMessageDrivenChannelAdapter.setQos(emqxMqttProperties.getQos());
        mqttPahoMessageDrivenChannelAdapter.setOutputChannel(getMqttMessageChannel());


        return mqttPahoMessageDrivenChannelAdapter;
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
