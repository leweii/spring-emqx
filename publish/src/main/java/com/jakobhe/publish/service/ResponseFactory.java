package com.jakobhe.publish.service;

import com.jakobhe.publish.response.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseFactory {

    private ResponseFactory() {
    }

    public static ResponseEntity<JsonResponse> onSuccess(JsonResponse t) {
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}
