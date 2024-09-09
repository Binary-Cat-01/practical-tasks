package com.walking.lesson64_thread_methods.task2_3.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimePrintingWithTracking implements Runnable {
    private final Duration duration;
    private final DateTimeFormatter dateTimeFormatter;
    private final Thread followedThread;

    public TimePrintingWithTracking(Duration duration, DateTimeFormatter dateTimeFormatter, Thread followedThread) {
        this.duration = duration;
        this.dateTimeFormatter = dateTimeFormatter;
        this.followedThread = followedThread;
    }

    @Override
    public void run() {
        while (!Thread.currentThread()
                      .isInterrupted() && followedThread.getState() != Thread.State.TERMINATED) {
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
