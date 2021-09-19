package com.jakobhe.publish.controller;

import com.jakobhe.publish.response.JsonResponse;
import com.jakobhe.publish.response.SuccessResponse;
import com.jakobhe.publish.service.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(name = "/checkhealth")
    public ResponseEntity<JsonResponse> checkhealth() {

        return ResponseFactory.onSuccess(SuccessResponse.builder().code(HttpStatus.OK.value()).message("success").build());
    }
}
