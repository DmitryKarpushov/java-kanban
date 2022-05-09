public class SubTask extends Task {
    String epicTask;
    int idEpic;

    public SubTask(String titleTask, String description, String statusTask, String epicTask, int idEpic) {
        super(titleTask, description, statusTask);
        this.epicTask = epicTask;
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "titleTask='" + titleTask + '\'' +
                ", description='" + description + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", epicTask='" + epicTask + '\'' +
                ", idEpic=" + idEpic + '\n' +
                '}';
    }

    public String getEpicTask() {
        return epicTask;
    }

    public void setEpicTask(String epicTask) {
        this.epicTask = epicTask;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}
