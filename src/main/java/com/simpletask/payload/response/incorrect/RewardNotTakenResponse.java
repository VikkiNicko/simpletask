package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class RewardNotTakenResponse extends Response {

    public RewardNotTakenResponse() {
        super("Reward for this task is not taken yet! Take your reward first");
    }
}
