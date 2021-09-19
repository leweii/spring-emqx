package com.jakobhe.publish.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse implements JsonResponse {
    @JsonProperty
    private Integer code;
    @JsonProperty
    private String message;
    @JsonProperty
    private long time;
    @JsonProperty
    private String path;
    @JsonProperty
    private Object reqPayload;
}
