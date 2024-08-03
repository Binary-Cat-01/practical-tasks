package com.walking.lesson50_stream_practice;

public class Customer {
    private final Long id;
    private final String name;
    private final Integer tier;

    public Customer() {
        this.id = 1L;
        this.name = "name";
        this.tier = 1;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTier() {
        return tier;
    }
}
