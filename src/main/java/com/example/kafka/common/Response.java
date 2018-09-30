package com.example.kafka.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response {
    private int code;
    private String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
