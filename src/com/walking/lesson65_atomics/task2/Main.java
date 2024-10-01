package com.walking.lesson65_atomics.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;

/**
 * Реализуйте метод, возвращающий число элементов равных N в двумерном массиве целых чисел.
 * Массив и N должны передаваться как параметры метода.
 * <p>
 * Каждый одномерный массив должен быть обработан в своем потоке.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long[][] table = getLongsTable();

        printArray(table);

        long result = countElementsEqualsExample(1, table);

        System.out.println(result);
    }

    private static long countElementsEqualsExample(long example, long[][] table) throws InterruptedException {
        LongAdder amount = new LongAdder();

        List<Thread> elementCounters = new ArrayList<>(table.length);

        for (long[] array : table) {
            Thread counter = new Thread(() -> amount.add(LongStream.of(array)
                                                                   .filter(l -> example == l)
                                                                   .count()), "counter");

            elementCounters.add(counter);

            counter.start();
        }

        for (Thread counter : elementCounters) {
            counter.join();
        }

        return amount.sum();
    }

    private static long[][] getLongsTable() {
        long[][] table = new long[5][5];

        Random random = new Random();

        for (long[] array : table) {
            Arrays.setAll(array, x -> random.nextLong(0, 3));
        }

        return table;
    }

    private static void printArray(long[][] table) {
        for (long[] array : table) {
            for (long l : array) {
                System.out.print(l + " ");
            }

            System.out.println();
        }
    }
}