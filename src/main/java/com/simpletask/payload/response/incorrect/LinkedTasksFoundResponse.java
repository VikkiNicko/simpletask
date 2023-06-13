package com.simpletask.payload.response.incorrect;

import com.simpletask.payload.response.Response;

public class LinkedTasksFoundResponse extends Response {
    public LinkedTasksFoundResponse() {
        super("You can't change reward while there are tasks attached to this reward." +
                " Finish them first or create another reward");
    }
}
