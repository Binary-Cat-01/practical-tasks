package com.walking.lesson64_thread_methods.task1;

import java.util.ArrayList;
import java.util.List;

/**
 * Напишите программу из 10 последовательно запускающихся потоков.
 * Каждый из этих потоков должен выводить в консоль сообщение вида
 * «%Имя потока% запущен и не спешит», вызывать yield(),
 * а после выводить сообщение «%Имя потока% завершен».
 * <p>
 * Обратите внимание на порядок вывода. Как он изменится, если убрать yield()?
 * Как изменится ситуация, если паре потоков выставить приоритет 10?
 */
public class Main {
    public static void main(String[] args) {
        initLazyThreads().forEach(Thread::start);
    }

    private static List<Thread> initLazyThreads() {
        List<Thread> lazyThreads = new ArrayList<>(10);

        Thread lazyThread1 = new Thread(printThreadStatus(), "lazyThread_1");
        lazyThread1.setPriority(10);
        lazyThreads.add(lazyThread1);

        for (int i = 1; i < 9; i++) {
            Thread currentThread = new Thread(printThreadStatus(), "lazyThread_" + (i + 1));
            lazyThreads.add(currentThread);
        }

        Thread lazyThread9 = new Thread(printThreadStatus(), "lazyThread_9");
        lazyThread9.setPriority(10);
        lazyThreads.add(lazyThread9);

        return lazyThreads;
    }

    private static Runnable printThreadStatus() {
        return () -> {
            String threadName = Thread.currentThread()
                                      .getName();

            System.out.println("%s запущен и не спешит".formatted(threadName));

            Thread.yield();

            System.out.println("%s завершен".formatted(threadName));
        };
    }
}