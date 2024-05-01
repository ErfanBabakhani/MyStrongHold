package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    CreateTask("Create", "^k create task --name=(?<name>\\S+)$"),
    CreateTaskByWorker("CreateByWorker", "^k create task --name=(?<name>\\S+) --node=(?<worker>\\d+)$"),
    DeleteTask("Delete", "^k delete task --name=(?<name>\\S+)$"),
    ;

    String regex;
    String name;

    Commands(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }

    public Matcher getTheMatcher(String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public String getName() {
        return name;
    }
}
