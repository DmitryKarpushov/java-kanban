import java.util.List;

public interface HistoryManager {//история задач

    void add(Task task);

    void remove(int id);
    //возвращает List тасков
    List<Task> getHistory();

}
