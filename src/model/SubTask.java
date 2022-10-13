package model;

import enums.*;

import java.util.List;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String title, String description, TaskStatus status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() + "," + getTaskType() + "," + getTitle() + "," + getStatus() + "," + getDescription() + "," + epicId;
    }

    public static SubTask fromString(List<String> line) {
        return new SubTask(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)), Integer.parseInt(line.get(5)));
    }

    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

}
