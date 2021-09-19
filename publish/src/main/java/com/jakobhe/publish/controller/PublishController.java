package com.jakobhe.publish.controller;

import com.jakobhe.publish.response.JsonResponse;
import com.jakobhe.publish.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private PublishService publishService;

    @PostMapping(name = "/publishEmqx")
    public ResponseEntity<String> publishEmqx(@RequestParam(value = "topic", defaultValue = "World") String topic,
                                              @RequestParam(value = "name", defaultValue = "World") String name) {

        publishService.sendToMqtt(topic, name);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
