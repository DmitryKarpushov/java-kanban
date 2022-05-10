import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private ArrayList<SubTask> epicSubtasks = new ArrayList<>();

    public Epic(String title, String description, String status, int id) {
        super(title, description, status, id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicSubtasks=" + epicSubtasks + '\n' +
                ", title='" + title + '\n' +
                ", description='" + description + '\n' +
                ", status='" + status + '\n' +
                ", id=" + id + '\n' +
                '}';
    }

    public ArrayList<SubTask> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(SubTask subTask) {
        this.epicSubtasks.add(subTask);
    }

    public void setListSubtasks(List<SubTask> listEpic) {
        this.epicSubtasks.addAll(listEpic);
    }

}
