package com.jakobhe.publish.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jakobhe.publish.properties.EmqxMqttProperties;
import com.jakobhe.publish.service.PublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    private PublishService publishService;
    @Autowired
    private EmqxMqttProperties emqxMqttProperties;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        publishService.sendToMqtt(emqxMqttProperties.defaultTopic, "hello world!");
    }
}
