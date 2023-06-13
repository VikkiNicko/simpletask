package com.simpletask.payload.response.success;

import com.simpletask.payload.response.Response;

public class SendToArchiveSuccessResponse extends Response {
    public SendToArchiveSuccessResponse() {
        super("Task successfully moved to archive");
    }
}
