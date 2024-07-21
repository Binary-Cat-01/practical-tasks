package com.walking.lesson40_queue2.model;

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