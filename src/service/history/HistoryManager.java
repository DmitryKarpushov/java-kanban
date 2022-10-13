package service.history;

import model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    /**
     * История состоит из задач, к которым были обращения за текущую сессию.
     *
     * @return Список задач в том порядке, в котором их запрашивали пользователи.
     */
    List<Task> getHistory();

}
