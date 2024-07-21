package com.walking.lesson40_queue2.service;

import com.walking.lesson40_queue2.model.Task;
import com.walking.lesson40_queue2.model.TaskStatus;
import com.walking.lesson40_queue2.util.Logger;

import java.util.*;

public class TaskService {
    private final Queue<Task> tasks;
    private final Logger logger;

    public TaskService() {
        this.tasks = new ArrayDeque<>();
        this.logger = new Logger();
    }

    public TaskService(Collection<? extends Task> collection) {
        this.tasks = new ArrayDeque<>(collection);
        this.logger = new Logger();

        for (Task task : collection) {
            logger.log(getTaskStatusMessage(task, TaskStatus.ACCEPTED));
        }
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return List.copyOf(tasks);
    }

    public Task getNext() {
        return tasks.peek();
    }

    public boolean hasNext() {
        return tasks.peek() != null;
    }

    public boolean accept(Task task) {
        if (tasks.offer(task)) {
            logger.log(getTaskStatusMessage(task, TaskStatus.ACCEPTED));
            return true;
        }

        return false;
    }

    public boolean acceptMany(Collection<? extends Task> collection) {
        if (this.tasks.addAll(collection)) {
            for (Task task : collection) {
                logger.log(getTaskStatusMessage(task, TaskStatus.ACCEPTED));
            }

            return true;
        }

        return false;
    }

    public Task executeNext() {
        Task polled = tasks.poll();

        if (polled != null) {
            logger.log(getTaskStatusMessage(polled, TaskStatus.EXECUTED));
        }

        return polled;
    }

    public List<Task> executeMany(int count) {
        if (count > size()) {
            count = size();
        }

        List<Task> taskList = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            taskList.add(executeNext());
        }

        return taskList;
    }

    public Task cancelNext() {
        Task polled = tasks.poll();

        if (polled != null) {
            logger.log(getTaskStatusMessage(polled, TaskStatus.CANCELED));
        }

        return polled;
    }

    public List<Task> cancelMany(int count) {
        if (count > size()) {
            count = size();
        }

        List<Task> taskList = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            taskList.add(cancelNext());
        }

        return taskList;
    }

    private String getTaskStatusMessage(Task task, TaskStatus status) {
        return "Task <%s> %s".formatted(task.getName(), status);
    }
}