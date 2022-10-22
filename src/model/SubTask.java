package model;

import enums.*;

import java.time.LocalDateTime;
import java.util.List;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String description, TaskStatus status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, TaskStatus status, int epicId,
                   LocalDateTime startTime, long duration) {
        super(title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() + "," + getTaskType() + "," + getTitle() + "," + getStatus() + "," + getDescription() + ","
                + epicId + "," + getStartTime() + "," + getDuration()+ "," + getEndTime();
    }

    public static SubTask fromString(List<String> line) {
        return new SubTask(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)),
                Integer.parseInt(line.get(5)),LocalDateTime.parse(line.get(6)),Long.parseLong(line.get(7)));
    }

    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

}
