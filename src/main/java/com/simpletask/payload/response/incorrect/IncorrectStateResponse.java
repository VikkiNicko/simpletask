package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class IncorrectStateResponse extends Response {
    public IncorrectStateResponse() {
        super("Error: Incorrect task state for this action");
    }
}
