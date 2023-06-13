package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class UserNotFoundResponse extends Response {
    public UserNotFoundResponse() {
        super("Error: User not found");
    }
}
