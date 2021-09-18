package com.jakobhe.springemqx.publish.controller;

import com.jakobhe.springemqx.publish.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("publish")
public class PublishController {


    @Autowired
    private PublishService publishService;

    @RequestMapping("emqxPublish")
    public String publishEmqx(String topic, String data) {

        publishService.sendToMqtt(topic, data);
        return "success";
    }
}
