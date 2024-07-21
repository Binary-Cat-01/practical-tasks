package com.walking.lesson41_set.task1.service;

import com.walking.lesson41_set.task1.model.Task;
import com.walking.lesson41_set.task1.model.TaskStatus;
import com.walking.lesson41_set.task1.util.Logger;

import java.time.Instant;
import java.util.*;

public class TaskService {
    private final NavigableSet<Task> tasks;
    private final Logger logger;

    public TaskService() {
        this.tasks = new TreeSet<>();
        this.logger = new Logger();
    }

    public TaskService(Collection<? extends Task> collection) {
        this.logger = new Logger();

        for (Task task : collection) {
            task.setAcceptedAt(Instant.now());
            logger.log(getTaskStatusMessage(task, TaskStatus.ACCEPTED));
        }

        this.tasks = new TreeSet<>(collection);
    }

    public int size() {
        return tasks.size();
    }

    public Task getNext() {
        return tasks.isEmpty() ? null : tasks.first();
    }

    public boolean hasNext() {
        return !tasks.isEmpty();
    }

    public List<Task> getAll() {
        return List.copyOf(tasks);
    }

    public boolean accept(Task task) {
        task.setAcceptedAt(Instant.now());

        if (tasks.add(task)) {
            logger.log(getTaskStatusMessage(task, TaskStatus.ACCEPTED));

            return true;
        }

        return false;
    }

    public boolean acceptMany(Collection<? extends Task> collection) {
        for (Task task : collection) {
            accept(task);
        }

        return true;
    }

    public Task executeNext() {
        Task polled = tasks.pollFirst();

        if (polled != null) {
            logger.log(getTaskStatusMessage(polled, TaskStatus.EXECUTED));
            polled.setAcceptedAt(null);
        }

        return polled;
    }

    public List<Task> executeMany(int count) {
        if (count > size()) {
            count = size();
        }

        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            taskList.add(executeNext());
        }

        return taskList;
    }

    public Task cancelNext() {
        Task polled = tasks.pollFirst();

        if (polled != null) {
            logger.log(getTaskStatusMessage(polled, TaskStatus.CANCELED));
            polled.setAcceptedAt(null);
        }

        return polled;
    }

    public List<Task> cancelMany(int count) {
        if (count > size()) {
            count = size();
        }

        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            taskList.add(cancelNext());
        }

        return taskList;
    }

    private String getTaskStatusMessage(Task task, TaskStatus status) {
        return "Task <%s> %s".formatted(task.getName(), status);
    }
}