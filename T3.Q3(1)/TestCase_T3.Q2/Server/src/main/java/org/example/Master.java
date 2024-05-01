package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Master {
    private static final ArrayList<Worker> allWorkers = new ArrayList<>();
    private static ArrayList<Task> allTask = new ArrayList<>();
    private static final ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream read;
    private static DataOutputStream write;

    static {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        allWorkers.add(new Worker(3, 1));
        allWorkers.add(new Worker(5, 2));
        allWorkers.add(new Worker(2, 3));
        for (int i = 0; i < allWorkers.size(); i++) {
            allWorkers.get(i).start();
        }
    }

    private static void run() {
        (new Thread(() -> {
            while (true) {
                String command = "";
                try {
                    command = read.readUTF();
                } catch (IOException e) {
                    Thread.currentThread().stop();
                }
                if (command.equals("k get tasks")) {
                    showAllTasks();
                    continue;
                } else if (command.equals("k get nodes")) {
                    showAllWorkers();
                    continue;
                }

                Commands theCommand = getTheCommandCommands(command);
                Matcher matcher;
                if (theCommand == null) {
                    writeAMessageToClient("wrong command!", Master.getSocket());
                    continue;
                }

                matcher = theCommand.getTheMatcher(command);
                if (theCommand.getName().equals("Create")) {
                    if (matcher.find())
                        addATask(new Task(matcher.group("name")));
                } else if (theCommand.getName().equals("CreateByWorker")) {
                    if (matcher.find()) {
                        int workerId = Integer.parseInt(matcher.group("worker"));
                        Worker worker = getTheWorkerById(workerId);
                        if (worker == null) {
                            writeAMessageToClient("Invalid worker id", Master.getSocket());
                            continue;
                        }
                        addATask(new Task(matcher.group("name")), worker);
                    }
                } else if (theCommand.getName().equals("Delete")) {
                    if (matcher.find()) {
                        writeAMessageToClient("We delete the task Name : " + matcher.group("name"), Master.getSocket());
                        deleteATask(new Task(matcher.group("name")));
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        })).start();
    }

    public static Worker getTheWorkerById(int id) {
        for (int i = 0; i < allWorkers.size(); i++) {
            if (allWorkers.get(i).getTheId() == id)
                return allWorkers.get(i);
        }
        return null;
    }

    public static Commands getTheCommandCommands(String command) {
        Matcher matcher;
        for (int i = 0; i < Commands.values().length; i++) {
            matcher = Commands.values()[i].getTheMatcher(command);
            if (matcher.find())
                return Commands.values()[i];
        }
        return null;
    }

    public static void main(String[] args) {
        Master master = new Master();
        while (true) {
            try {
                socket = serverSocket.accept();
                writeAMessageToClient("Salam Khosh Omadi", socket);
                read = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                write = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static synchronized void writeAMessageToClient(String message, Socket socket) {
        try {
            DataOutputStream write = new DataOutputStream(socket.getOutputStream());
            write.writeUTF(message);
            write.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static synchronized void addATask(Task task) {
        Worker worker = findTheFreeWorker();
        if (worker == null) {
            writeAMessageToClient("No available worker your task is in pending !", Master.getSocket());
            task.setTaskStatus("Pending");
            allWorkers.get(0).getAllPendingTask().add(task);
        } else {
            writeAMessageToClient("Your task is running", Master.getSocket());
            worker.getAllTask().add(task);
        }
        allTask.add(task);
    }

    public static synchronized void addATask(Task task, Worker worker) {
        if (worker.getAllTask().size() >= worker.getMAX_TASK_NUMBER()) {
            writeAMessageToClient("No available worker your task is in pending !", Master.getSocket());
            task.setTaskStatus("Pending");
            worker.getAllPendingTask().add(task);
        } else {
            writeAMessageToClient("Your task is running", Master.getSocket());
            worker.getAllTask().add(task);
        }
        allTask.add(task);
    }

    private static void showAllTasks() {
        String out = "All task :\n";
        for (int i = 0; i < allTask.size(); i++) {
            out += "Name " + allTask.get(i).getName();
            out += " Status " + allTask.get(i).getTaskStatus() + "\n";
        }
        if (allTask.size() == 0)
            out += "No task !";
        Master.writeAMessageToClient(out, Master.getSocket());
    }

    private static void showAllWorkers() {
        String out = "All workers :\n";
        for (int i = 0; i < allWorkers.size(); i++) {
            out += "Name : " + allWorkers.get(i).getName();
            out += "Id : " + allWorkers.get(i).getId();
            out += "Ip : 127.0.0.1 port 8080\n";
        }
        writeAMessageToClient(out, Master.getSocket());
    }

    private static synchronized void deleteATask(Task task) {
        allTask.remove(task);
        removeFromWorkerTask(task.getName());
    }

    private static synchronized void removeFromWorkerTask(String taskName) {
        for (int i = 0; i < allWorkers.size(); i++) {
            for (int j = 0; j < allWorkers.get(i).getAllTask().size(); j++) {
                if (allWorkers.get(i).getAllTask().get(j).getName().equals(taskName))
                    allWorkers.get(i).getAllTask().remove(allWorkers.get(i).getAllTask().get(j));
            }
        }
    }

    private static synchronized Worker findTheFreeWorker() {
        for (int i = 0; i < allWorkers.size(); i++) {
            if (allWorkers.get(i).getAllTask().size() >= allWorkers.get(i).getMAX_TASK_NUMBER())
                continue;
            return allWorkers.get(i);
        }
        return null;
    }

}
