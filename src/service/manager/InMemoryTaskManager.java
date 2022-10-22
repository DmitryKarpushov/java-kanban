package service.manager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import enums.*;
import model.*;
import service.history.HistoryManager;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Comparator<Task> comparatorTasks = Comparator.comparing(Task::getStartTime);
    protected Set<Task> prioritizedTasks = new TreeSet<>(comparatorTasks);
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private Integer idTask = 0;

    @Override
    public void addTasks(Task task) {
        idTask = generateId();
        task.setId(idTask);
        task.setEndTime(task.getEndTime());
        if (isNoIntersections(task)) {
            tasks.put(idTask, task);
            prioritizedTasks.add(task);
        } else {
            System.err.println("Задача пересекается по времени с уже существующей");
        }
    }

    @Override
    public void addEpic(Epic epic) {
        idTask = generateId();
        epic.setId(idTask);
        epics.put(idTask, epic);
        updateStatus(epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        idTask = generateId();
        subTask.setId(idTask);
        if (isNoIntersections(subTask)) {
            prioritizedTasks.add(subTask);
            subTasks.put(idTask, subTask);
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                updateStatus(epic);
                updateTimeEpic(epic);
            }
        } else {
            System.err.println("Задача пересекается по времени с уже существующей");
        }
    }

    @Override
    public List<SubTask> getSubtasksEpicId(int id) {
        if (epics.containsKey(id)) {
            List<SubTask> subtasksEpic = new ArrayList<>();
            Epic epic = epics.get(id);
            for (Map.Entry<Integer, SubTask> key : subTasks.entrySet()) {
                if (key.getValue().getEpicId() == epic.getId()) {
                    subtasksEpic.add(key.getValue());
                }
            }
            return subtasksEpic;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            task.setId(task.getId());
            if (isNoIntersections(task)) {
                prioritizedTasks.add(task);
            } else {
                System.err.println("Задача пересекается по времени с уже существующей");
            }
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }

    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
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
    public void deleteTasks() {
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            historyManager.remove(entry.getKey());
        }
        prioritizedTasks.clear();
        tasks.clear();
    }

    @Override
    public void deleteTask(Integer id) {
        prioritizedTasks.removeIf(task -> task.getId() == id);
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void deleteEpics() {
        if (!epics.isEmpty()) {
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                historyManager.remove(entry.getKey());
            }
            subTasks.clear();
            epics.clear();
        } else {
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
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateStatus(epics.get(epic.getId()));
            updateStatus(epic);
            updateTimeEpic(epic);
            if (isNoIntersections(epic)) {
                prioritizedTasks.add(epic);
            } else {
                System.err.println("Задача пересекается по времени с уже существующей");
            }
        } else {
            System.err.println("updateEpic.Введите другой id, так как данный id не найден.");
        }
    }

    @Override
    public void updateTimeEpic(Epic epic) {
        List<SubTask> subtasks = getSubtasksEpicId(epic.getId());
        LocalDateTime startTime = subtasks.get(0).getStartTime();
        LocalDateTime endTime = subtasks.get(0).getEndTime();

        for (SubTask subtask : subtasks) {
            if (subtask.getStartTime().isBefore(startTime)) {
                startTime = subtask.getStartTime();
            }
            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        long duration = ChronoUnit.MINUTES.between(startTime,endTime);
        epic.setDuration(duration);
    }


    @Override
    public void deleteEpic(Integer id) {
        Epic epic = epics.get(id);
        if (epics.containsKey(id)) {
            epic.getSubtasks().forEach(subtaskId -> {
                prioritizedTasks.removeIf(task -> Objects.equals(task.getId(), subtaskId));
                subTasks.remove(subtaskId);
                Integer a = Integer.parseInt(String.valueOf(subtaskId));
                historyManager.remove(a);
            });
            historyManager.remove(id);
            epics.remove(id);
        } else {
            System.err.println("Список задач Epic и так пуст");
        }
    }

    @Override
    public void deleteSubtasks() {
        Iterator<Task> iter = prioritizedTasks.iterator();
            while (iter.hasNext()) {
                Task next = iter.next();
                if (next.getTaskType().equals(TaskType.SUBTASK)) {
                    iter.remove();
                }
            }
        subTasks.clear();
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            value.getStatus().equals(String.valueOf(TaskStatus.NEW));
            value.getSubtasks().clear();
            historyManager.remove(entry.getKey());
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
    public void updateSubtask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            Epic epic = epics.get(subTask.getEpicId());
            subTasks.put(subTask.getId(), subTask);
            updateStatus(epic);
            updateTimeEpic(epic);
            if (isNoIntersections(subTask)) {
                prioritizedTasks.add(subTask);
            } else {
                System.err.println("Задача пересекается по времени с уже существующей");
            }
        } else {
            System.err.println("Введите другой id, так как данный id не найден.");
        }
    }

    @Override
    public void deleteSubtask(Integer id) {
        SubTask subtask = subTasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epics.get(subtask.getEpicId()).getSubtasks().remove(id);
        updateStatus(epic);
        updateTimeEpic(epic);
        prioritizedTasks.remove(subtask);
        historyManager.remove(id);
        subTasks.remove(id);
    }

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    private void updateStatus(Epic epic) {
        if (epic != null) {
            List<SubTask> value = getSubtasksEpicId(epic.getId());
            for (SubTask subTask : value) {
                if ((value == null)) {
                    epic.setStatus(TaskStatus.NEW);
                } else if (subTask.getStatus().equals(TaskStatus.NEW)) {
                    epic.setStatus(TaskStatus.NEW);
                } else if (subTask.getStatus().equals(TaskStatus.DONE)) {
                    epic.setStatus(TaskStatus.DONE);
                } else {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        }
    }

    /**
     * Данный метод оставил,тк при удалении всех сабтасков я прохожу по каждому эпику и
     * начинаю чистить подзадачи,в местах где можно проверять только один эпик сделал
     * */
    private void updateStatus() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            for (SubTask subtask : value.getSubtasks()) {
                if ((entry.getValue().getSubtasks() == null)) {
                    entry.getValue().setStatus(TaskStatus.NEW);
                } else if (subtask.getStatus().equals(TaskStatus.NEW)) {
                    entry.getValue().setStatus(TaskStatus.NEW);
                } else if (subtask.getStatus().equals(TaskStatus.DONE)) {
                    entry.getValue().setStatus(TaskStatus.DONE);
                } else {
                    entry.getValue().setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        }
    }

    public void addToHistory(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
        } else if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
        } else if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
        }
    }

    @Override
    public List<Task> getHistoryView() {
        return historyManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    public boolean isNoIntersections(Task task) {
        if (!prioritizedTasks.isEmpty()) {
            for (Task prioritizedTask : prioritizedTasks) {
                if ((task.getStartTime().isAfter(prioritizedTask.getStartTime())|| task.getStartTime().equals(prioritizedTask.getStartTime())) &&
                        (task.getEndTime().isBefore(prioritizedTask.getEndTime()))|| task.getEndTime().equals(prioritizedTask.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }

}
