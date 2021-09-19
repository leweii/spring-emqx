package com.jakobhe.publish.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jakob.mqtt.emqx")
public class EmqxMqttProperties {

    public String username;
    public String password;
    public String hostUrl;
    public String clientId;
    public String defaultTopic;
    public int timeout;
    public int keepAlive;
    public int qos;
}
