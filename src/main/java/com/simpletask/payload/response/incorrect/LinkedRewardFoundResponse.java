package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class LinkedRewardFoundResponse extends Response {
    public LinkedRewardFoundResponse() {
        super("Linked reward found. You cannot change task's level while there is reward pin to it. " +
                "Unpin reward before changing task's level or create a new task");
    }
}
