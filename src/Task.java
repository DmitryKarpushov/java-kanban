public class Task {
    String titleTask;
    String description;
    String statusTask;

    public Task(String titleTask, String description, String statusTask) {
        this.titleTask = titleTask;
        this.description = description;
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "titleTask='" + titleTask + '\n' +
                ", description='" + description + '\n' +
                ", statusTask='" + statusTask + '\n' +
                '}';
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }
}
