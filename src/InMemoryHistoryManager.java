import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Object> viewTask = new ArrayList<>();

    @Override
    public void add(Object id) {
        viewTask.add(id);
        if (viewTask.size() > 9) {
            viewTask.remove(0);
        }
    }

    @Override
    public List<Object> getHistory() {
        return viewTask;
    }


    public void setViewTask(ArrayList<Object> viewTask) {
        this.viewTask = viewTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryHistoryManager that = (InMemoryHistoryManager) o;
        return Objects.equals(viewTask, that.viewTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewTask);
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "viewTask=" + viewTask +
                '}';
    }
}
