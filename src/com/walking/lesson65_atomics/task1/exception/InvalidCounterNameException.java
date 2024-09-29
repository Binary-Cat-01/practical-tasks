package com.walking.lesson65_atomics.task1.exception;

public class InvalidCounterNameException extends RuntimeException {
    public InvalidCounterNameException(String counterName) {
        super("Не удалось найти счетчик с указанным именем: %s".formatted(counterName));
    }
}
