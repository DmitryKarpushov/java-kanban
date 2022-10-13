import model.Epic;
import model.SubTask;
import model.Task;
import service.filedao.FileBackedTasksManager;
import enums.*;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        Path path = Path.of("test.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);


        Task taskOne = new Task("Уборка", "Погладить вещи", TaskStatus.NEW);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе", TaskStatus.NEW);
        fileBackedTasksManager.addTasks(taskOne);
        fileBackedTasksManager.addTasks(taskTwo);

        Epic epicTwo = new Epic("Университет", "Написать диплом", TaskStatus.NEW);
        fileBackedTasksManager.addEpic(epicTwo);

        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", TaskStatus.IN_PROGRESS, epicTwo.getId());
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", TaskStatus.NEW, epicTwo.getId());
        SubTask subTaskThree = new SubTask("Написать введение", "текст", TaskStatus.NEW, epicTwo.getId());

        fileBackedTasksManager.addSubTask(subTaskOne);
        fileBackedTasksManager.addSubTask(subTaskTwo);
        fileBackedTasksManager.addSubTask(subTaskThree);

        epicTwo.addSubtasks(subTaskOne);
        epicTwo.addSubtasks(subTaskTwo);
        epicTwo.addSubtasks(subTaskThree);

        System.out.println(fileBackedTasksManager.getTaskById(1));
        System.out.println(fileBackedTasksManager.getTaskById(2));

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println("2.2 Вывод всего списка задач Epic: ");
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println("2.3 Вывод всего списка задач Subtask: ");
        System.out.println(fileBackedTasksManager.getSubTasks());
        System.out.println("==========================================================");

        System.out.println("==========================================================getHistoryView");
        System.out.println(fileBackedTasksManager.getHistoryView());
        System.out.println("==========================================================");
        System.out.println("==========================================================");


        FileBackedTasksManager fileBackedTasksManagerSecond = new FileBackedTasksManager(file);
        fileBackedTasksManagerSecond.loadFromFile(file);



        System.out.println(fileBackedTasksManagerSecond.getTaskById(1));
        System.out.println(fileBackedTasksManagerSecond.getTaskById(2));

        System.out.println("fileBackedTasksManagerSecond==========================================================getHistoryView");
        System.out.println(fileBackedTasksManagerSecond.getHistoryView());



    }
}
