package com.walking.lesson65_atomics.task1.service;

import com.walking.lesson65_atomics.task1.exception.CounterNotFound;
import com.walking.lesson65_atomics.task1.exception.InvalidCounterNameException;
import com.walking.lesson65_atomics.task1.model.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CounterService {
    private final List<Counter> counters;

    public CounterService(List<Counter> counters) {
        this.counters = new ArrayList<>(counters);
    }

    public Stream<Counter> getAllCounters() {
        return counters.stream();
    }

    public void addCounter(Counter counter) {
        counters.add(counter);
    }

    public void removeCounter(Counter counter) {
        counters.remove(counter);
    }

    public Optional<Counter> getCounterByName(String name) {
        return counters.parallelStream()
                       .filter(counter -> counter.getName()
                                                 .equals(name))
                       .findAny();
    }

    public int increaseCounter(String name, int value) {
        return getCounterByName(name).orElseThrow(() -> new InvalidCounterNameException(name))
                                     .increase(value);
    }

    public int increaseCounter(Counter counter, int delta) {
        return Optional.ofNullable(counter)
                       .orElseThrow(() -> new CounterNotFound(counter))
                       .increase(delta);
    }

    public int decreaseCounter(String name, int value) {
        return getCounterByName(name).orElseThrow(() -> new InvalidCounterNameException(name))
                                     .decrease(value);
    }

    public int decreaseCounter(Counter counter, int value) {
        return Optional.ofNullable(counter)
                       .orElseThrow(() -> new CounterNotFound(counter))
                       .decrease(value);
    }

    public int incrementCounter(String name) {
        return getCounterByName(name).orElseThrow(() -> new InvalidCounterNameException(name))
                                     .increment();
    }

    public int incrementCounter(Counter counter) {
        return Optional.ofNullable(counter)
                       .orElseThrow(() -> new CounterNotFound(counter))
                       .increment();
    }

    public int decrementCounter(String name) {
        return getCounterByName(name).orElseThrow(() -> new InvalidCounterNameException(name))
                                     .decrement();
    }

    public int decrementCounter(Counter counter) {
        return Optional.ofNullable(counter)
                       .orElseThrow(() -> new CounterNotFound(counter))
                       .decrement();
    }

    public boolean reset(String name) {
        return getCounterByName(name).orElseThrow(() -> new InvalidCounterNameException(name))
                                     .reset();
    }

    public boolean reset(Counter counter) {
        return Optional.ofNullable(counter)
                       .orElseThrow(() -> new CounterNotFound(counter))
                       .reset();
    }
}
