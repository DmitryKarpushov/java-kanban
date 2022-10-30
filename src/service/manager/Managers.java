package service.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.filedao.FileBackedTasksManager;
import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;
import service.http.HttpTaskManager;
import service.http.KVServer;
import service.http.adapter.LocalDateTimeReadAndWrite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static KVServer getDefaultKVServer() throws IOException {
        return new KVServer();
    }

    public static HttpTaskManager getDefaultHttpTaskManager() {
        return new HttpTaskManager();
    }

    public static FileBackedTasksManager getDefaultFileBackedTasks() {
        Path path = Path.of("test.csv");
        File file = new File(String.valueOf(path));
        return new FileBackedTasksManager(file);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeReadAndWrite());
        return gsonBuilder.create();
    }
}
