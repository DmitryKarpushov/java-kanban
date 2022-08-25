package Manager;

import Task.Epic;
import Task.Task;

import java.util.List;
import Task.SubTask;
// Работа с задачами/
public interface TaskManager {

    void addTasks(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    List<SubTask> getEpicSubtasks(Integer id);

    void deleteTasks();

    void deleteTask(Integer id);

    void deleteEpics();

    void deleteEpic(Integer id);

    void deleteSubtask();

    void deleteSubtask(Integer id, Integer idEpic);

    Task getTaskById(Integer id);

    Task getEpicById(Integer id);

    Task getSubtaskById(Integer id);

    void updateEpic(Epic epic, List<SubTask> listEpic);

    void updateSubtask(SubTask subTask, Integer idEpic, SubTask subTaskPrev);

    void updateTask(Task task);

    List<Task> getHistoryView();
}
