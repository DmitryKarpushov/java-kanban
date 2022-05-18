import java.util.List;

public interface HistoryManager {

    int MAX_VALUE = 10;

    void add(Task task);

    List<Task> getHistory();

}
