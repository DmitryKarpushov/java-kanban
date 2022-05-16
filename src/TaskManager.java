import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    //2.4 создание Task
    void addTasks(Task task);

    //2.4 создание Epic
    void addEpic(Epic epic);

    //2.4 создание SubTask
    void addSubTask(SubTask subTask);

    //2.5 Обновление задачи
    void updateTask(Integer id, Task task);

    // 2.1 Получение списка всех Task.
    HashMap<Integer, Task> printAllTask(HashMap<Integer, Task> hashMap);

    // 2.1 Получение списка всех Epic.
    HashMap<Integer, Epic> printAllTaskEpic(HashMap<Integer, Epic> hashMap);

    // 2.1 Получение списка всех SubTask.
    HashMap<Integer, SubTask> printAllSubTask(HashMap<Integer, SubTask> hashMap);

    //2.3 Получение задачи по id
    Task getTaskById(Integer id);

    //2.2 Удаление всех задач Task.
    HashMap<Integer, Task> deleteAllTask(HashMap<Integer, Task> hashMap);

    //2.6 Удаление по идентификатору Task.
    HashMap<Integer, Task> deleteTask(HashMap<Integer, Task> hashMap, Integer id);

    //2.2 Удаление всех задач Epic и его же подзадач.
    HashMap<Integer, Epic> deleteAllEpic(HashMap<Integer, Epic> hashMap);

    //2.3 Получение Epic по id
    Epic getEpicById(Integer id);

    // Для обновления
    ArrayList<SubTask> getEpicSubtasks(Integer id);

    //2.5 Обновление задачи Epic
    void updateEpic(Integer id, Epic epic, ArrayList<SubTask> listEpic);

    //2.6 Удаление по идентификатору Epic.
    HashMap<Integer, Epic> deleteEpic(Integer id);

    //2.2 Удаление всех задач Subtask.
    HashMap<Integer, SubTask> deleteAllSubtask(HashMap<Integer, SubTask> hashMap);

    //2.3 Получение подзадачи по id
    Task getSubtaskById(Integer id);

    //2.5 Обновление подзадачи
    void updateSubtask(Integer id, SubTask subTask, Integer idEpic, SubTask subTask1);

    //2.6 Удаление по идентификатору Subtask.
    HashMap<Integer, SubTask> deleteSubtask(HashMap<Integer, SubTask> hashMap, Integer id, Integer idEpic, SubTask subTask1);

    ArrayList<SubTask> printTaskEpic(Integer id);

}
