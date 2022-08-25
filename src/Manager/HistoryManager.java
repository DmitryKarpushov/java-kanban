package Manager;
import Task.Task;

import java.util.List;

public interface HistoryManager {//история задач

    void add(Task task);

    void remove(int id);

    /**
     * История состоит из задач, к которым были обращения за текущую сессию..
     *
     * @return Список задач в том порядке, в котором их запрашивали пользователи.
     */
    List<Task> getHistory();

}
