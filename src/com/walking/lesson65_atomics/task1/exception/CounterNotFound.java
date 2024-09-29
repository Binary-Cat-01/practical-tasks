package com.walking.lesson65_atomics.task1.exception;

import com.walking.lesson65_atomics.task1.model.Counter;

public class CounterNotFound extends RuntimeException {
    public CounterNotFound(Counter counter) {
        super("Счетчик <%s> не найден".formatted(counter));
    }
}
