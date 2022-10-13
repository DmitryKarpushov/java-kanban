package model;

import Enum.*;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<SubTask> subtasks = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
    }

    @Override
    public String toString() {
        return "Epic{" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\''+
                ", id='" + getId() + '\''+
                ",subtasks='" + subtasks + '\''+
                '}';
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
