package com.simpletask.payload.response.success;

import com.simpletask.payload.response.Response;

public class RewardTakenResponse extends Response {
    public RewardTakenResponse(String rewardText) {
        super("Your reward: " + rewardText + ". Enjoy!");
    }
}
