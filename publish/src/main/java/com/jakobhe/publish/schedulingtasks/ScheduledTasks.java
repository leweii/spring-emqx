package com.jakobhe.publish.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakobhe.publish.message.Led;
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


    private int pin = 0;
    @Autowired
    private PublishService publishService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        Map<String, String> params = new HashMap<>();
        params.put("pinStatus", pin == 1 ? "ON" : "OFF");
        Gson gson = new GsonBuilder().create();
        publishService.sendToMqtt("jakob_topic",
                gson.toJson(Led.builder().method("method").params(params).clientToken("token")));

        pin = pin == 0 ? 1 : 0;
    }
}
