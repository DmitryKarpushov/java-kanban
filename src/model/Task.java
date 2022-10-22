package model;

import enums.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Task {
    private String title; // Заголовок
    private String description; // Описание
    private TaskStatus status; // Статус
    private int id; // id задачи
    private LocalDateTime startTime; // дата, когда предполагается приступить к выполнению задачи.
    private long duration; // продолжительность задачи, оценка того, сколько времени она займёт в минутах (число);
    private LocalDateTime endTime; // время завершения задачи, которое рассчитывается исходя из startTime и duration

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description, TaskStatus status, LocalDateTime startTime, long duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return id + "," + getTaskType() + "," + title + "," + status + "," + description+ "," + startTime + "," + duration+ "," +getEndTime();
    }

    public static Task fromString(List<String> line) {
        return new Task(line.get(2), line.get(4), TaskStatus.valueOf(line.get(3)), LocalDateTime.parse(line.get(5)),Long.parseLong(line.get(6)));
    }

    /**
     * Заметка для себя
     *
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

}
