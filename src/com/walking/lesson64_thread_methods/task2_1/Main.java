package com.walking.lesson64_thread_methods.task2_1;

import com.walking.lesson64_thread_methods.model.TimePrinting;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

/**
 * Напишите программу, заполняющую двумерный массив большого размера
 * (подберите на свой вкус, ограничения могут зависеть от заданного размера хипа в JVM)
 * случайными числами. Параллельно должен работать поток,
 * каждые 100 миллисекунд пишущий в консоль текущее время.
 * <p>
 * Программа должна завершиться, как только массив будет заполнен.
 * Предоставьте три различных решения данной программы. (Первое)
 */
public class Main {
    public static void main(String[] args) {
        int[][] tableForFilling = new int[10_000][100_000];
        Random randomNumber = new Random();

        var timePrinting = new TimePrinting(Duration.ofMillis(100), DateTimeFormatter.ofPattern("KK:mm:ss:SSS"));

        Thread timePrinter = new Thread(timePrinting, "timePrinter");

        timePrinter.setDaemon(true);
        timePrinter.start();

        for (int[] row : tableForFilling) {
            Arrays.setAll(row, _ -> randomNumber.nextInt());
        }
    }
}