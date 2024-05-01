package org.example;

import java.util.ArrayList;

public class Worker extends Thread {
    private int id;
    private ArrayList<Task> allTask = new ArrayList<>();
    private ArrayList<Task> allPendingTask = new ArrayList<>();
    private Integer MAX_TASK_NUMBER;

    public Worker(Integer MAX_TASK_NUMBER, Integer id) {
        this.id = id;
        this.MAX_TASK_NUMBER = MAX_TASK_NUMBER;
    }

    @Override
    public void run() {
        while (true) {
            doATask();
        }
    }

    private synchronized void doATask() {
        if (allTask.size() == 0) {
            if (allPendingTask.size() != 0) {
                System.out.println("RR");
                System.out.println("Start worker << id : " + this.id + " >>" + " to do Task << name : " + allTask.get(0));
                System.out.println("In time : " + System.currentTimeMillis() + " Millis");
                changeTheTaskStatus(allPendingTask);
            }
            try {
                Thread.currentThread().wait(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        System.out.println("Start worker << id : " + this.id + " >>" + " to do Task << name : " + allTask.get(0).getName() + " >>");
        System.out.println("In time : " + System.currentTimeMillis() + " Millis");
        changeTheTaskStatus(allTask);
    }

    private synchronized void changeTheTaskStatus(ArrayList<Task> tasks) {

        try {
            Thread.currentThread().wait(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Master.writeAMessageToClient("Done!", Master.getSocket());
        tasks.get(0).setTaskStatus("Done");
        tasks.remove(tasks.get(0));
        if (allPendingTask.size() > 0)
            allPendingTask.get(0).setTaskStatus("Running");

    }


    public Integer getMAX_TASK_NUMBER() {
        return MAX_TASK_NUMBER;
    }

    public ArrayList<Task> getAllTask() {
        return allTask;
    }

    public ArrayList<Task> getAllPendingTask() {
        return allPendingTask;
    }

    public int getTheId() {
        return id;
    }
}
