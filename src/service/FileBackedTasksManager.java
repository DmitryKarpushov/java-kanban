package service;

import Manager.HistoryManager;
import Manager.Managers;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Дмитрий Карпушов 31.08.2022
 */

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    //private final HistoryManager historyManager = Managers.getDefaultHistory();
//    public FileBackedTasksManager(HistoryManager historyManager, File file) {
//        super(historyManager);
//        this.file = file;
//    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось найти файл");
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : getTasks()) {
                writer.println(toStringTask(task));
            }
            for (Epic epic : getEpics()) {
                writer.println(toStringEpic(epic));
            }

            for (SubTask subtask : getSubTasks()) {
                writer.println(toStringSubTask(subtask));
            }
            writer.println();

//            System.out.println("не помогает " + getHistoryView());
          //  writer.write(historyToString(getHistoryManager()));
            writer.println();
            writer.write(historyToString(getHistoryManager()));
            writer.println();
            //writer.write(historyToString(historyManager));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось сохранить файл");
        }

    }

    // метод сохранения задачи в строку SubTask
    private String toStringSubTask(SubTask subtask) {
        return subtask.getId() + "," + subtask.getTaskType() + "," + subtask.getTitle() + "," + subtask.getStatus() + "," + subtask.getDescription() + "," + subtask.getEpicId();
    }

    // метод сохранения задачи в строку Epic
    private String toStringEpic(Epic epic) {
        return epic.getId() + "," + epic.getTaskType() + "," + epic.getTitle() + "," + epic.getStatus() + "," + epic.getDescription();
    }

    // метод сохранения задачи в строку Task
    private String toStringTask(Task task) {
        return task.getId() + "," + task.getTaskType() + "," + task.getTitle() + "," + task.getStatus() + "," + task.getDescription();
    }

    @Override
    public void addTasks(Task task) {
        super.addTasks(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

    @Override
    public Task getTaskById(Integer id) {
        return super.getTaskById(id);
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public Epic getEpicById(Integer id) {
        return super.getEpicById(id);
    }

    @Override
    public List<SubTask> getEpicSubtasks(Integer id) {
        return super.getEpicSubtasks(id);
    }

    @Override
    public void updateEpic(Epic epic, List<SubTask> listEpic) {
        super.updateEpic(epic, listEpic);
    }

    @Override
    public void deleteTask(Integer id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(Integer id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask() {
        super.deleteSubtask();
        save();
    }

    @Override
    public void deleteSubtasks(Integer id, Integer idEpic) {
        super.deleteSubtasks(id, idEpic);
        save();
    }

    @Override
    public Task getSubtaskById(Integer id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void updateSubtask(SubTask subTask, Integer idEpic, SubTask subTaskPrev) {
        super.updateSubtask(subTask, idEpic, subTaskPrev);
    }

    // Метод для сохранения истории в CSV
    private static String historyToString(HistoryManager historyManager) {
        List<Task> history = historyManager.getHistory();
        //System.out.println("FileBackedTasksManager.historyToString.getHistoryView " + getHistoryView());
        System.out.println("FileBackedTasksManager.historyToString.history(historyManager.getHistory()) " + history);
        StringBuilder str = new StringBuilder();

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }
        if (str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
            str.append(".");
        }
        return str.toString();
    }

}
