import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    Integer idTask = 0;

    @Override
    public void addTasks(Task task) {
        idTask = generateId();
        tasks.put(idTask, task);
        task.setId(idTask);
    }

    @Override
    public void addEpic(Epic epic) {
        idTask = generateId();
        epics.put(idTask, epic);
        epic.setId(idTask);
        updateStatus(epics.get(idTask));
    }

    @Override
    public void addSubTask(SubTask subTask) {
        idTask = generateId();
        subTasks.put(idTask, subTask);
        subTask.setId(idTask);
    }

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

    @Override
    public Map<Integer, Task> printAllTask() {
        if (!tasks.isEmpty()) {
            return tasks;
        } else {
            System.out.println("Список задач Task пуст! ");
            System.err.println("Список задач Task пуст! ");
            return null;
        }
    }

    @Override
    public Map<Integer, Epic> printAllTaskEpic() {
        if (!epics.isEmpty()) {
            return epics;
        } else {
            System.out.println("Список задач Epic пуст! ");
            System.err.println("Список задач Epic пуст! ");
            return null;
        }
    }

    @Override
    public Map<Integer, SubTask> printAllSubTask() {
        if (!subTasks.isEmpty()) {
            return subTasks;
        } else {
            System.out.println("Список задач SubTask пуст! ");
            System.err.println("Список задач SubTask пуст! ");
            return null;
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteTask(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void deleteAllEpic() {
        if (!epics.isEmpty()) {
            subTasks.clear();
            epics.clear();
        } else {
            System.out.println("Список задач Epic и так пуст");
            System.err.println("Список задач Epic и так пуст");
        }
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    @Override
    public List<SubTask> getEpicSubtasks(Integer id) {
        if (epics.containsKey(id)) {
            return epics.get(id).getSubtasks();
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    @Override
    public void updateEpic(Integer id, Epic epic, List<SubTask> listEpic) {
        if (epics.containsKey(id)) {
            epics.put(id, epic);
            epic.setId(id);
            epic.setAllSubtasks(listEpic);
            updateStatus(epics.get(id));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    @Override
    public void deleteEpic(Integer id) {
        if (epics.containsKey(id)) {
            System.out.println("Удалили Эпик: " + epics.get(id));
            epics.remove(id);
            Iterator<Map.Entry<Integer, SubTask>> iter = subTasks.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, SubTask> entry = iter.next();
                if (entry.getValue().getEpicId() == id) { //1
                    iter.remove();
                }
            }
        } else {
            System.out.println("Список задач Epic и так пуст");
            System.err.println("Список задач Epic и так пуст");
        }
    }

    @Override
    public void deleteAllSubtask() {
        subTasks.clear();
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            value.getStatus().equals(String.valueOf(Status.NEW));
            value.getSubtasks().clear();
        }
        updateStatus();
    }

    @Override
    public Task getSubtaskById(Integer id) {
        if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
            return null;
        }
    }

    @Override
    public void updateSubtask(Integer id, SubTask subTask, Integer idEpic, SubTask subTaskPrev) {
        if (subTasks.containsKey(id)) {
            subTasks.put(id, subTask);
            subTask.setId(id);
            epics.get(idEpic).getSubtasks().remove(subTaskPrev);
            epics.get(idEpic).addSubtasks(subTask);
            updateStatus(epics.get(idEpic));
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    @Override
    public void deleteSubtask(Integer id, Integer idEpic, SubTask subTask) {
        subTasks.remove(id);
        epics.get(idEpic).getSubtasks().remove(subTask);
        updateStatus(epics.get(idEpic));
    }

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    private void updateStatus(Epic epic) {
        List<SubTask> value = epic.getSubtasks();
        for (SubTask subTask : value) {
            if ((value == null)) {
                epic.setStatus(Status.NEW);
            } else if (subTask.getStatus().equals(Status.NEW)) {
                epic.setStatus(Status.NEW);
            } else if (subTask.getStatus().equals(Status.DONE)) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    //Данный метод оставил,тк при удалении всех сабтасков я прохожу по каждому эпику и
    // начинаю чистить подзадачи,в местах где можно проверять только один эпик сделал
    private void updateStatus() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            for (SubTask subtask : value.getSubtasks()) {
                if ((entry.getValue().getSubtasks() == null)) {
                    entry.getValue().setStatus(Status.NEW);
                } else if (subtask.getStatus().equals(Status.NEW)) {
                    entry.getValue().setStatus(Status.NEW);
                } else if (subtask.getStatus().equals(Status.DONE)) {
                    entry.getValue().setStatus(Status.DONE);
                } else {
                    entry.getValue().setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

}
