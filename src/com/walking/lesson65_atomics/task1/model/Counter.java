package com.walking.lesson65_atomics.task1.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter implements Comparable<Counter> {
    private final String name;
    private final String unit;

    private final AtomicInteger value;

    public Counter(String name, String unit, int value) {
        this.name = name;
        this.unit = unit;
        this.value = new AtomicInteger(value);
    }

    public Counter(String name, String unit) {
        this(name, unit, 0);
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value.get();
    }

    public void setValue(int newValue) {
        value.set(newValue);
    }

    public int increase(int delta) {
        return value.addAndGet(delta);
    }

    public int decrease(int delta) {
        return value.addAndGet(-delta);
    }

    public int increment() {
        return value.incrementAndGet();
    }

    public int decrement() {
        return value.decrementAndGet();
    }

    public boolean reset() {
        value.set(0);

        return true;
    }

    @Override
    public String toString() {
        return "Counter{" + "name='" + name + '\'' + ", unit='" + unit + '\'' + ", value=" + value + '}';
    }

    @Override
    public int compareTo(Counter counter) {
        return name.compareTo(counter.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Counter counter = (Counter) o;

        return Objects.equals(name, counter.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}