package com.pro.task_management.event;

import com.pro.task_management.entity.Task;

public class TaskDeadlineEvent {
    private final Task task;

    public TaskDeadlineEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}