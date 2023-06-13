package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class UserExistsResponse extends Response{
    public UserExistsResponse() {
        super("User already exists");
    }
}
