package com.walking.lesson41_set.task1.model;

public enum TaskStatus {
    ACCEPTED("accepted"),
    EXECUTED("executed"),
    CANCELED("canceled");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}