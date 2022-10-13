package model;

import Enum.*;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String title, String description, Status status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" + '\'' +
                "epicId=" + epicId + '\''+
                ", title='" + getStatus() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id='" + getId() + '\''+ '\n' +
                '}';
    }

    public TaskType getTaskType(){
        return TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

}
