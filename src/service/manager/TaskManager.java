package service.manager;

import model.Epic;
import model.Task;

import java.util.List;
import model.SubTask;
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

    void deleteSubtasks();

    void deleteSubtask(Integer id);

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    Task getSubtaskById(Integer id);

    void updateEpic(Epic epic);

    void updateSubtask(SubTask subTask);

    void updateTask(Task task);

    List<Task> getHistoryView();

    List<Task> getPrioritizedTasks();

    List<SubTask> getSubtasksEpicId(int id);
    void updateTimeEpic(Epic epic);
}
