public class SubTask extends Task {
    String epicTask;
    int epicId;

    public SubTask(String title, String description, String status, int id, String epicTask, int epicId) {
        super(title, description, status, id);
        this.epicTask = epicTask;
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicTask='" + epicTask + '\'' +
                ", epicId=" + epicId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id + '\n' +
                '}';
    }

    public String getEpicTask() {
        return epicTask;
    }

    public void setEpicTask(String epicTask) {
        this.epicTask = epicTask;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
