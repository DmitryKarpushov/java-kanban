import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<SubTask> subtasks = new ArrayList<>();

    public Epic(String title, String description, String status) {
        super(title, description, status);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks='" + subtasks + '\''+
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\''+
                ", id='" + getId() + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(SubTask subTask) {
        this.subtasks.add(subTask);
    }

    public void setListSubtasks(List<SubTask> listEpic) {
        this.subtasks.addAll(listEpic);
    }

}
