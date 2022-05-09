import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private ArrayList<SubTask> listSubtasks = new ArrayList<>();

    public Epic(String titleTask, String description, String statusTask) {
        super(titleTask, description, statusTask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "listSubtasks=" + listSubtasks + '\n' +
                ", titleTask='" + titleTask + '\'' + '\n' +
                ", description='" + description + '\'' + '\n' +
                ", statusTask='" + statusTask + '\'' + '\n' +
                '}';
    }

    public ArrayList<SubTask> getListSubtasks() {
        return listSubtasks;
    }

    public void setListSubtasks(SubTask subTask) {
        this.listSubtasks.add(subTask);
    }

    public void setListSubtasks(List<SubTask> listEpic) {
        this.listSubtasks.addAll(listEpic);
    }

}
