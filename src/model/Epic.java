package model;

import enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subtasks = new ArrayList<>();
    private transient LocalDateTime endTime;

    public Epic(String title, String description, TaskStatus status) {
        super(title, description, status);
    }

    public Epic(String title, String description, TaskStatus status, LocalDateTime startTime, long duration, LocalDateTime endTime) {
        super(title, description, status, startTime, duration);
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return getId() + "," + getTaskType() + "," + getTitle() + "," + getStatus() + "," + getDescription() + "," + getStartTime() + "," + getDuration() + "," + getEndTime();
    }

    public static Epic fromString(List<String> line) {
        return new Epic(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)), LocalDateTime.parse(line.get(5)), Long.parseLong(line.get(6)) ,LocalDateTime.parse(line.get(7)));
    }

    public TaskType getTaskType() {
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
