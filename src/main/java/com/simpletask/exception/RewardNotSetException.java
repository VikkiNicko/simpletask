package com.simpletask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RewardNotSetException extends RuntimeException {
    public RewardNotSetException() {
        super("Reward was not found!");
    }
}
