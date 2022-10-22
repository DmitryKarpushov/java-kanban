package service.filedao;

import model.*;
import service.exception.FileReadErrorException;
import service.exception.FileWriteErrorException;
import service.history.HistoryManager;
import service.manager.InMemoryTaskManager;

import java.io.*;
import java.util.*;

/**
 * @author Дмитрий Карпушов 31.08.2022
 */
public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    private void save() {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write("id,type,name,status,description,startTime,duration,endTime\n");

            for (Task task : getTasks()) {
                writer.println(task.toString());
            }
            for (Epic epic : getEpics()) {
                writer.println(epic.toString());
            }

            for (SubTask subtask : getSubTasks()) {
                writer.println(subtask.toString());
            }
            writer.println();
            writer.write(historyToString(getHistoryManager()));
            writer.println();
        } catch (IOException e) {
            throw new FileWriteErrorException("Не удалось считать данные из файла.");
        }
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


    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Task getSubtaskById(Integer id) {
        Task subTask = super.getSubtaskById(id);
        save();
        return subTask;
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
    public List<SubTask> getEpicSubtasks(Integer id) {
        return super.getEpicSubtasks(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
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
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteSubtask(Integer id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(SubTask subTask) {
        super.updateSubtask(subTask);
        save();
    }

    private String historyToString(HistoryManager historyManager) {
        List<Task> history = historyManager.getHistory();
        StringBuilder str = new StringBuilder();

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }
        if (str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public void loadFromFile(File file) {
        try (BufferedReader readerCSV = new BufferedReader(new FileReader(file))) {
            String lineCSV = readerCSV.readLine();
            while (readerCSV.ready()) {

                lineCSV = readerCSV.readLine();
                if (lineCSV.isEmpty()) {
                    break;
                }
                Task task = historyFromString(lineCSV);

                if (task.getTaskType().toString().equals("SUBTASK")) {
                    addSubTask((SubTask) task);
                } else if (task.getTaskType().toString().equals("EPIC")) {
                    addEpic((Epic) task);
                } else {
                    addTasks(task);
                }
            }
            String lineWithHistory = readerCSV.readLine();
            for (Integer id : historyViewFromString(lineWithHistory)) {
                addToHistory(id);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileReadErrorException("Не удалось считать файл");
        }

    }

    static List<Integer> historyViewFromString(String value) {
        List<Integer> historyView = new ArrayList<>();
        if (!value.isEmpty()) {
            List<String> id = List.of(value.split(","));
            for (String number : id) {
                historyView.add(Integer.valueOf(number));
            }
        }
        return historyView;
    }

    private Task historyFromString(String value) {
        List<String> line = List.of(value.split(","));
        if (line.get(1).equals("SUBTASK")) {
            return SubTask.fromString(line);
        } else if (line.get(1).equals("EPIC")) {
            return Epic.fromString(line);
        } else {
            return Task.fromString(line);
        }
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

}
