package com.walking.lesson41_set.task1;

import com.walking.lesson41_set.task1.model.Task;
import com.walking.lesson41_set.task1.service.TaskService;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализуйте задачу
 * <a href="https://github.com/KFalcon2022/practical-tasks/tree/master/src/com/walking/lesson40_queue2">...</a>,
 * используя Set, вместо Queue.
 * Продумайте, как можно эффективно реализовать задачу,
 * чтобы сохранить принцип FIFO при обработке заданий.
 */
public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService(initTasks());

        taskService.accept(new Task("Fourth"));
        taskService.executeNext();

        if (taskService.hasNext()) {
            taskService.cancelNext();
        }

        taskService.executeMany(2);
        taskService.acceptMany(initTasks());

        List<Task> canceledTasks = taskService.cancelMany(2);
        System.out.println("Canceled tasks:");

        for (Task canceledTask : canceledTasks) {
            System.out.println(canceledTask.getName());
        }

        Task nextTask = taskService.getNext();
        System.out.println("Next task: " + nextTask.getName());
    }

    public static List<Task> initTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task("First"));
        tasks.add(new Task("Second"));
        tasks.add(new Task("Third"));

        return tasks;
    }
}