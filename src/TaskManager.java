import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    Integer idTask = 0;
    int id;

    //Генерация ID
    private Integer generateId() {
        idTask++;
        return idTask;
    }

    //2.4 создание Task
    public void addTasks(Task task) {
        id = generateId();//Комментарий 6.
        tasks.put(id, task);
        task.setId(id);
    }

    //2.4 создание Epic
    public void addEpic(Epic epic) {
        id = generateId();
        epics.put(id, epic);
        epic.setId(id);
        //просчитываем статус одного пришедшего эпика.
        updateStatus(epics.get(id));
    }

    //2.4 создание SubTask
    public void addSubTask(SubTask subTask) {
        id = generateId();
        subTasks.put(id, subTask);
        subTask.setId(id);
    }

    //2.5 Обновление задачи
    public void updateTask(Integer id, Task task) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
            task.setId(id);
            System.out.println(tasks.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }

    }

    // 2.1 Получение списка всех Task.
    public HashMap<Integer, Task> printAllTask(HashMap<Integer, Task> hashMap) {
        if (!hashMap.isEmpty()) {
            return hashMap;
        } else {
            System.out.println("Список задач Task пуст! ");
            System.err.println("Список задач Task пуст! ");
            return null;
        }
    }

    // 2.1 Получение списка всех Epic.
    public HashMap<Integer, Epic> printAllTaskEpic(HashMap<Integer, Epic> hashMap) {
        if (!hashMap.isEmpty()) {
            return hashMap;
        } else {
            System.out.println("Список задач Epic пуст! ");
            System.err.println("Список задач Epic пуст! ");
            return null;
        }
    }

    // 2.1 Получение списка всех SubTask.
    public HashMap<Integer, SubTask> printAllSubTask(HashMap<Integer, SubTask> hashMap) {
        if (!hashMap.isEmpty()) {
            return hashMap;
        } else {
            System.out.println("Список задач SubTask пуст! ");
            System.err.println("Список задач SubTask пуст! ");
            return null;
        }
    }

    //2.3 Получение задачи по id
    public Task getTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.2 Удаление всех задач Task.
    public HashMap<Integer, Task> deleteAllTask(HashMap<Integer, Task> hashMap) {
        hashMap.clear();
        return hashMap;
    }

    //2.6 Удаление по идентификатору Task.
    public HashMap<Integer, Task> deleteTask(HashMap<Integer, Task> hashMap, Integer id) {
        hashMap.remove(id);
        return hashMap;
    }

    //2.2 Удаление всех задач Epic и его же подзадач.
    public HashMap<Integer, Epic> deleteAllEpic(HashMap<Integer, Epic> hashMap) {
        if (!hashMap.isEmpty()) {
            getSubTasks().clear();
            hashMap.clear();
            return hashMap;
        } else {
            System.out.println("Список задач Epic и так пуст");
            System.err.println("Список задач Epic и так пуст");
            return null;
        }
    }

    //2.3 Получение Epic по id
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            System.out.println("ID = " + id);
            return epics.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    // Для обновления
    public ArrayList<SubTask> getEpicSubtasks(Integer id) {
        if (epics.containsKey(id)) {
            System.out.println("ID = " + id);
            return epics.get(id).getSubtasks();
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление задачи Epic
    public void updateEpic(Integer id, Epic epic, ArrayList<SubTask> listEpic) {
        //проверка есть ли данный id
        if (epics.containsKey(id)) {
            epics.put(id, epic);
            epic.setId(id);
            epic.setListSubtasks(listEpic);
            updateStatus(epics.get(id));
            //Тестирование
            System.out.println(epics.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    //2.6 Удаление по идентификатору Epic.
    public HashMap<Integer, Epic> deleteEpic(Integer id) {
        if (epics.containsKey(id)) {
            System.out.println("Удалили Эпик: " + epics.get(id));
            epics.remove(id);
            Iterator<Map.Entry<Integer, SubTask>> iter = subTasks.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, SubTask> entry = iter.next();
                if (entry.getValue().epicId == id) { //1
                    iter.remove();
                }
            }
            //updateStatus();
            System.out.println("Осталось: ");
            return epics;
        } else {
            System.out.println("Список задач Epic и так пуст");
            System.err.println("Список задач Epic и так пуст");
            return null;
        }
    }

    //2.2 Удаление всех задач Subtask.
    public HashMap<Integer, SubTask> deleteAllSubtask(HashMap<Integer, SubTask> hashMap) {
        hashMap.clear();
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            // if (!value.status.equals("NEW")) {
            value.getStatus().equals("NEW");
            value.getSubtasks().clear();
            // }
        }
        updateStatus();
        return hashMap;
    }

    //2.3 Получение подзадачи по id
    public Task getSubtaskById(Integer id) {
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление подзадачи
    public void updateSubtask(Integer id, SubTask subTask, Integer idEpic, SubTask subTask1) {
        //проверка есть ли данный id
        if (subTasks.containsKey(id)) {
            subTasks.put(id, subTask);
            subTask.setId(id);
            epics.get(idEpic).getSubtasks().remove(subTask1);
            epics.get(idEpic).setSubtasks(subTask);
            updateStatus(epics.get(idEpic));
            //Тестирование
            System.out.println(subTasks.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    //2.6 Удаление по идентификатору Subtask.
    public HashMap<Integer, SubTask> deleteSubtask(HashMap<Integer, SubTask> hashMap, Integer id, Integer idEpic, SubTask subTask1) {
        hashMap.remove(id);
        epics.get(idEpic).getSubtasks().remove(subTask1);
        System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!" + epics.get(idEpic));
        updateStatus(epics.get(idEpic));
        return hashMap;
    }

    public ArrayList<SubTask> printTaskEpic(Integer id) {
        return epics.get(id).getSubtasks();
    }

    private void updateStatus(Epic epic) {
        ArrayList<SubTask> value = epic.getSubtasks();
        for (SubTask listSubtask : value) {
            if ((value == null)) {
                epic.setStatus("NEW");
            } else if (listSubtask.getStatus().equals("NEW")) {
                epic.setStatus("NEW");
            } else if (listSubtask.getStatus().equals("DONE")) {
                epic.setStatus("DONE");
            } else {
                epic.setStatus("IN_PROGRESS");
            }
        }
    }

    //Данный метод оставил,тк при удалении всех сабтасков я прохожу по каждому эпику и
    // начинаю чистить подзадачи,в местах где можно проверять только один эпик сделал
    private void updateStatus() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            for (SubTask listSubtask : value.getSubtasks()) {
                if ((entry.getValue().getSubtasks() == null)) {
                    entry.getValue().setStatus("NEW");
                } else if (listSubtask.getStatus().equals("NEW")) {
                    entry.getValue().setStatus("NEW");
                } else if (listSubtask.getStatus().equals("DONE")) {
                    entry.getValue().setStatus("DONE");
                } else {
                    entry.getValue().setStatus("IN_PROGRESS");
                }
            }
        }
    }


    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(HashMap<Integer, SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }
}
