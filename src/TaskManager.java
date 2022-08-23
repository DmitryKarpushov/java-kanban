import java.util.List;
// Работа с задачами
public interface TaskManager {

    void addTasks(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    void updateTask(Integer id, Task task);

    List<Task> getAllTasks();

    List<Epic> getAllTaskEpic();

    List<SubTask> getAllSubTask();

    Task getTaskById(Integer id);

    void deleteAllTask();

    void deleteTask(Integer id);

    void deleteAllEpic();

    Epic getEpicById(Integer id);

    List<SubTask> getEpicSubtasks(Integer id);

    void updateEpic(Integer id, Epic epic, List<SubTask> listEpic);

    void deleteEpic(Integer id);

    void deleteAllSubtask();

    Task getSubtaskById(Integer id);

    void updateSubtask(Integer id, SubTask subTask, Integer idEpic, SubTask subTaskPrev);

    void deleteSubtask(Integer id, Integer idEpic, SubTask subTask);

    List<Task> getHistoryManager();
}
