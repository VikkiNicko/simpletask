package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class TaskNotFoundResponse extends Response {
    public TaskNotFoundResponse() {
        super("Error: Task not found");
    }
}
