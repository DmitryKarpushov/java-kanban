import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> viewTask = new ArrayList<>();

    @Override
    public void add(Task task) {
        viewTask.add(task);
        if (viewTask.size() == MAX_VALUE) {
            viewTask.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return viewTask;
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
