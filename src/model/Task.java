package model;

import enums.*;

import java.util.List;
import java.util.Objects;

public class Task {
    private String title; //Заголовок
    private String description; //Описание
    private TaskStatus status; //Статус
    private int id; //id задачи

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }


    @Override
    public String toString() {
        return id + "," + getTaskType() + "," + title + "," + status + "," + description;
    }

    public static Task fromString(List<String> line) {
        return new Task(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)));
    }

    /**
     * Заметка для себя
     * <p>
     * Достаточно определить equals/hashCode в главном классе
     * У потомков данного класса переопределять данные методы не нужно
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, status, id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

}
