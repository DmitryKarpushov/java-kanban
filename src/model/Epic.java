package model;

import enums.*;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<SubTask> subtasks = new ArrayList<>();

    public Epic(String title, String description, TaskStatus status) {
        super(title, description, status);
    }

    @Override
    public String toString() {
        return getId() + "," + getTaskType() + "," + getTitle() + "," + getStatus() + "," + getDescription() + ",";
    }

    public static Epic fromString(List<String> line) {
        return new Epic(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)));
    }

    public TaskType getTaskType(){
        return TaskType.EPIC;
    }
    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void addSubtasks(SubTask subTask) {
        this.subtasks.add(subTask);
    }

    public void setAllSubtasks(List<SubTask> listEpic) {
        this.subtasks.addAll(listEpic);
    }

}
