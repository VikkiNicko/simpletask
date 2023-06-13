package com.simpletask.payload.response.success;

import com.simpletask.payload.response.Response;

public class MarkDoneSuccessResponse extends Response {
    public MarkDoneSuccessResponse() {
        super("Well done!");
    }

    public MarkDoneSuccessResponse(String rewardText) {
        super("Well done! Your reward is waiting for you");
    }
}
