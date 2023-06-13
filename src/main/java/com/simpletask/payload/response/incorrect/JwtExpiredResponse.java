package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class JwtExpiredResponse extends Response {
    public JwtExpiredResponse() {
        super("Error: your token had expired! Please login again to get a new token");
    }
}
