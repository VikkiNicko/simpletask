package com.simpletask.model;

public enum Level {
    EMPTY(""),
    DAILY("Daily"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String label;
    Level(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}




