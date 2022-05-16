import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    Integer idTask = 0;
    int id;

    //2.4 создание Task
    @Override
    public void addTasks(Task task) {
        id = generateId();//Комментарий 6.
        tasks.put(id, task);
        task.setId(id);
    }

    //2.4 создание Epic
    @Override
    public void addEpic(Epic epic) {
        id = generateId();
        epics.put(id, epic);
        epic.setId(id);
        //просчитываем статус одного пришедшего эпика.
        updateStatus(epics.get(id));
    }

    //2.4 создание SubTask
    @Override
    public void addSubTask(SubTask subTask) {
        id = generateId();
        subTasks.put(id, subTask);
        subTask.setId(id);
    }

    //2.5 Обновление задачи
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public Task getTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            //inMemoryHistoryManager.add(tasks.get(id));
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.2 Удаление всех задач Task.
    @Override
    public HashMap<Integer, Task> deleteAllTask(HashMap<Integer, Task> hashMap) {
        hashMap.clear();
        return hashMap;
    }

    //2.6 Удаление по идентификатору Task.
    @Override
    public HashMap<Integer, Task> deleteTask(HashMap<Integer, Task> hashMap, Integer id) {
        hashMap.remove(id);
        return hashMap;
    }

    //2.2 Удаление всех задач Epic и его же подзадач.
    @Override
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
    @Override
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            //System.out.println("ID = " + id);
            //inMemoryHistoryManager.add(epics.get(id));
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    // Для обновления
    @Override
    public ArrayList<SubTask> getEpicSubtasks(Integer id) {
        if (epics.containsKey(id)) {
            //System.out.println("ID = " + id);
            return epics.get(id).getSubtasks();
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление задачи Epic
    @Override
    public void updateEpic(Integer id, Epic epic, ArrayList<SubTask> listEpic) {
        //проверка есть ли данный id
        if (epics.containsKey(id)) {
            epics.put(id, epic);
            epic.setId(id);
            epic.setListSubtasks(listEpic);
            updateStatus(epics.get(id));
            //Тестирование
            //System.out.println(epics.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    //2.6 Удаление по идентификатору Epic.
    @Override
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

    //2.2 Удаление всех задач Subtask
    @Override
    public HashMap<Integer, SubTask> deleteAllSubtask(HashMap<Integer, SubTask> hashMap) {
        hashMap.clear();
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            // if (!value.status.equals("NEW")) {
            value.getStatus().equals(String.valueOf(Status.NEW));
            value.getSubtasks().clear();
            // }
        }
        updateStatus();
        return hashMap;
    }

    //2.3 Получение подзадачи по id
    @Override
    public Task getSubtaskById(Integer id) {
        if (subTasks.containsKey(id)) {
           // inMemoryHistoryManager.add(subTasks.get(id));
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    //2.5 Обновление подзадачи
    @Override
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
    @Override
    public HashMap<Integer, SubTask> deleteSubtask(HashMap<Integer, SubTask> hashMap, Integer id, Integer idEpic, SubTask subTask1) {
        hashMap.remove(id);
        epics.get(idEpic).getSubtasks().remove(subTask1);
        //System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!" + epics.get(idEpic));
        updateStatus(epics.get(idEpic));
        return hashMap;
    }

    @Override
    public ArrayList<SubTask> printTaskEpic(Integer id) {
        return epics.get(id).getSubtasks();
    }

    //Генерация ID
    private Integer generateId() {
        idTask++;
        return idTask;
    }

    //Обновление статуса
    private void updateStatus(Epic epic) {
        ArrayList<SubTask> value = epic.getSubtasks();
        for (SubTask listSubtask : value) {
            if ((value == null)) {
                epic.setStatus(String.valueOf(Status.NEW));
            } else if (listSubtask.getStatus().equals(String.valueOf(Status.NEW))) {
                epic.setStatus(String.valueOf(Status.NEW));
            } else if (listSubtask.getStatus().equals(String.valueOf(Status.DONE))) {
                epic.setStatus(String.valueOf(Status.DONE));
            } else {
                epic.setStatus(String.valueOf(Status.IN_PROGRESS));
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
                    entry.getValue().setStatus(String.valueOf(Status.NEW));
                } else if (listSubtask.getStatus().equals(String.valueOf(Status.NEW))) {
                    entry.getValue().setStatus(String.valueOf(Status.NEW));
                } else if (listSubtask.getStatus().equals(String.valueOf(Status.DONE))) {
                    entry.getValue().setStatus(String.valueOf(Status.DONE));
                } else {
                    entry.getValue().setStatus(String.valueOf(Status.IN_PROGRESS));
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

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

}
