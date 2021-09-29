package com.jakobhe.publish.message;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Led {

    private String method;
    private String clientToken;
    private Map<String, String> params;
}
