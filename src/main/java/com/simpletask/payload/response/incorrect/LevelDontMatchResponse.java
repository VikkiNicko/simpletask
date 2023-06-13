package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class LevelDontMatchResponse extends Response {
    public LevelDontMatchResponse() {
        super("Task's level don't match chosen reward's level. Please choose another reward.");
    }
}
