package org.example;

public class Task {
    private String taskStatus;
    private String name;

    public Task(String name) {
        this.name = name;
        setTaskStatus("Running");
    }

    public synchronized void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public String getName() {
        return name;
    }
}
