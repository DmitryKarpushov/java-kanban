package service.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import service.filedao.FileBackedTasksManager;
import service.history.InMemoryHistoryManager;
import service.manager.Managers;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Дмитрий Карпушов 24.10.2022
 */
public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient client;
    private final Gson gson;
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    public HttpTaskManager() {
        super(null);
        this.gson = Managers.getGson();
        this.client = new KVTaskClient();
    }

    @Override
    public void loadFromFile(File file) {
        Type tasksType = new TypeToken<List<Task>>(){}.getType();
        List<Task> tasks = gson.fromJson(client.load("tasks"), tasksType);
        if (tasks != null) {
            tasks.forEach(task -> {
                int id = task.getId();
                this.tasks.put(id, task);
                this.prioritizedTasks.add(task);
                if (id > idTask) {
                    idTask = id;
                }
            });
        }

        Type subtasksType = new TypeToken<List<SubTask>>(){}.getType();
        List<SubTask> subtasks = gson.fromJson(client.load("subtasks"), subtasksType);
        if (subtasks != null) {
            subtasks.forEach(subtask -> {
                int id = subtask.getId();
                this.subTasks.put(id, subtask);
                this.prioritizedTasks.add(subtask);
                if (id > idTask) {
                    idTask = id;
                }
            });
        }

        Type epicsType = new TypeToken<List<Epic>>() {
        }.getType();
        List<Epic> epics = gson.fromJson(client.load("epics"), epicsType);
        if (epics != null) {
            epics.forEach(epic -> {
                int id = epic.getId();
                this.epics.put(id, epic);
                this.prioritizedTasks.add(epic);
                if (id > idTask) {
                    idTask = id;
                }
            });
        }

        Type historyType = new TypeToken<List<Task>>(){}.getType();
        List<Task> history = gson.fromJson(client.load("history"), historyType);

        if (history != null) {
            for (Task task:history) {
                inMemoryHistoryManager.add(this.findTask(task.getId()));
            }
        }
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(getTasks());
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(getEpics());
        client.put("epics", jsonEpics);
        String jsonSubTask = gson.toJson(getSubTasks());
        client.put("subtasks" , jsonSubTask);
        String jsonHistoryView = gson.toJson(getHistoryView());
        client.put("history" , jsonHistoryView);
    }
    protected Task findTask(Integer id) {
        if (tasks.get(id) != null) {
            return tasks.get(id);
        }
        if (epics.get(id) != null) {
            return epics.get(id);
        }
        return subTasks.get(id);
    }
}
