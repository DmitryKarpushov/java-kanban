import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskManager {
    private HashMap<Integer, Task> mapOfTasks = new HashMap<>();
    private HashMap<Integer, SubTask> mapOfSubTasks = new HashMap<>();
    private HashMap<Integer, Epic> mapOfEpics = new HashMap<>();
    Integer idTask = 0;

    //Генерация ID
    private Integer generateId() {
        idTask++;
        return idTask;
    }

    //2.4 создание Task
    public void addTasks(Task task) {
        mapOfTasks.put(generateId(), task);
    }

    //2.4 создание Epic
    public void addEpic(Epic epic) {
        mapOfEpics.put(generateId(), epic);
        //    updateStatus();
    }

    //2.4 создание SubTask
    public void addSubTask(SubTask subTask) {
        mapOfSubTasks.put(generateId(), subTask);
    }

    //2.5 Обновление задачи
    public void updateTask(Integer id, Task task) {
        if (mapOfTasks.containsKey(id)) {
            mapOfTasks.put(id, task);
            System.out.println(mapOfTasks.get(id));
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
        if (mapOfTasks.containsKey(id)) {
            return mapOfTasks.get(id);
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
            getMapOfSubTasks().clear();
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
        if (mapOfEpics.containsKey(id)) {
            System.out.println("ID = " + id);
            return mapOfEpics.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    // Для обновления
    public ArrayList<SubTask> getEpicSubtasks(Integer id) {
        if (mapOfEpics.containsKey(id)) {
            System.out.println("ID = " + id);
            return mapOfEpics.get(id).getEpicSubtasks();
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление задачи Epic
    public void updateEpic(Integer id, Epic epic, ArrayList<SubTask> listEpic) {
        //проверка есть ли данный id
        if (mapOfEpics.containsKey(id)) {
            mapOfEpics.put(id, epic);
            epic.setListSubtasks(listEpic);
            updateStatus(mapOfEpics.get(id));
            //Тестирование
            System.out.println(mapOfEpics.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    //2.6 Удаление по идентификатору Epic.
    public HashMap<Integer, Epic> deleteEpic(Integer id) {
        if (mapOfEpics.containsKey(id)) {
            System.out.println("Удалили Эпик: " + mapOfEpics.get(id));
            mapOfEpics.remove(id);
            Iterator<Map.Entry<Integer, SubTask>> iter = mapOfSubTasks.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, SubTask> entry = iter.next();
                if (entry.getValue().epicId == id) { //1
                    iter.remove();
                }
            }
            //updateStatus();
            System.out.println("Осталось: ");
            return mapOfEpics;
        } else {
            System.out.println("Список задач Epic и так пуст");
            System.err.println("Список задач Epic и так пуст");
            return null;
        }
    }

    //2.2 Удаление всех задач Subtask.
    public HashMap<Integer, SubTask> deleteAllSubtask(HashMap<Integer, SubTask> hashMap) {
        hashMap.clear();
        for (Map.Entry<Integer, Epic> entry : mapOfEpics.entrySet()) {
            Epic value = entry.getValue();
            // if (!value.status.equals("NEW")) {
            value.status = "NEW";
            value.getEpicSubtasks().clear();
            // }
        }
        updateStatus();
        return hashMap;
    }

    //2.3 Получение подзадачи по id
    public Task getSubtaskById(Integer id) {
        if (mapOfSubTasks.containsKey(id)) {
            return mapOfSubTasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление подзадачи
    public void updateSubtask(Integer id, SubTask subTask, Integer idEpic, SubTask subTask1) {
        //проверка есть ли данный id
        if (mapOfSubTasks.containsKey(id)) {
            mapOfSubTasks.put(id, subTask);
            mapOfEpics.get(idEpic).getEpicSubtasks().remove(subTask1);
            mapOfEpics.get(idEpic).setEpicSubtasks(subTask);
            updateStatus(mapOfEpics.get(idEpic));
            //Тестирование
            System.out.println(mapOfSubTasks.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    //2.6 Удаление по идентификатору Subtask.
    public HashMap<Integer, SubTask> deleteSubtask(HashMap<Integer, SubTask> hashMap, Integer id, Integer idEpic, SubTask subTask1) {
        hashMap.remove(id);
        mapOfEpics.get(idEpic).getEpicSubtasks().remove(subTask1);
        System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!" + mapOfEpics.get(idEpic));
        updateStatus(mapOfEpics.get(idEpic));
        return hashMap;
    }

    public ArrayList<SubTask> printTaskEpic(Integer id) {
        return mapOfEpics.get(id).getEpicSubtasks();
    }

    private void updateStatus(Epic epic) {
        ArrayList<SubTask> value1 = epic.getEpicSubtasks();
        for (SubTask listSubtask : value1) {
            if ((value1 == null)) {
                epic.status = "NEW";
            } else if (listSubtask.status.equals("NEW")) {
                epic.status = "NEW";
            } else if (listSubtask.status.equals("DONE")) {
                epic.status = "DONE";
            } else {
                epic.status = "IN_PROGRESS";
            }
        }
    }
    //Данный метод оставил,тк при удалении всех сабтасков я прохожу по каждому эпику и
    // начинаю чистить подзадачи,в местах где можно проверять только один эпик сделал
    private void updateStatus() {
        for (Map.Entry<Integer, Epic> entry : mapOfEpics.entrySet()) {
            Epic value = entry.getValue();
            for (SubTask listSubtask : value.getEpicSubtasks()) {
                if ((entry.getValue().getEpicSubtasks() == null)) {
                    entry.getValue().status = "NEW";
                } else if (listSubtask.status == "NEW") {
                    entry.getValue().status = "NEW";
                } else if (listSubtask.status == "DONE") {
                    entry.getValue().status = "DONE";
                } else {
                    entry.getValue().status = "IN_PROGRESS";
                }
            }
        }
    }


    public HashMap<Integer, Task> getMapOfTasks() {
        return mapOfTasks;
    }

    public void setMapOfTasks(HashMap<Integer, Task> mapOfTasks) {
        this.mapOfTasks = mapOfTasks;
    }

    public HashMap<Integer, SubTask> getMapOfSubTasks() {
        return mapOfSubTasks;
    }

    public void setMapOfSubTasks(HashMap<Integer, SubTask> mapOfSubTasks) {
        this.mapOfSubTasks = mapOfSubTasks;
    }

    public HashMap<Integer, Epic> getMapOfEpics() {
        return mapOfEpics;
    }

    public void setMapOfEpics(HashMap<Integer, Epic> mapOfEpics) {
        this.mapOfEpics = mapOfEpics;
    }
}
