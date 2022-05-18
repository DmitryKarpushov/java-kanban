import java.util.ArrayList;
import java.util.List;

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
    public String toString() {
        return "InMemoryHistoryManager{" +
                "viewTask=" + viewTask +
                '}';
    }
}
