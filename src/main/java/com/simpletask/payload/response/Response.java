package com.simpletask.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }
}
