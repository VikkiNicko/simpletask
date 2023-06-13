package com.simpletask.payload.response.success;

import com.simpletask.payload.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DeleteTaskSuccessResponse extends Response {
    public DeleteTaskSuccessResponse() {
       super("Task deleted successfully");
    }
}
