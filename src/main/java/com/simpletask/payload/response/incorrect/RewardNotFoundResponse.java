package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class RewardNotFoundResponse extends Response {
    public RewardNotFoundResponse() {
        super("Error: Reward not found");
    }
}
