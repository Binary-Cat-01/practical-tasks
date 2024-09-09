package com.walking.lesson64_thread_methods.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimePrinting implements Runnable {
    private final Duration duration;
    private final DateTimeFormatter dateTimeFormatter;

    public TimePrinting(Duration duration, DateTimeFormatter dateTimeFormatter) {
        this.duration = duration;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void run() {
        while (!Thread.currentThread()
                      .isInterrupted()) {
            System.out.println(LocalDateTime.now()
                                            .format(dateTimeFormatter));

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread()
                      .interrupt();
            }
        }
    }
}
