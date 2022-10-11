import Manager.Managers;
import model.Epic;
import model.SubTask;
import model.Task;
import service.FileBackedTasksManager;
import Enum.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        TaskManager manager = Managers.getDefault();
//        Test test = new Test();
//        test.test(manager);
        Path path = Path.of("test.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(/*Managers.getDefaultHistory()*/ file);


        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ", Status.NEW);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", Status.NEW);
        fileBackedTasksManager.addTasks(taskOne);
        fileBackedTasksManager.addTasks(taskTwo);

        Epic epicTwo = new Epic("Университет", "Написать диплом", Status.NEW);
        fileBackedTasksManager.addEpic(epicTwo);

        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", Status.IN_PROGRESS, epicTwo.getId());
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", Status.NEW, epicTwo.getId());
        SubTask subTaskThree = new SubTask("Написать введение", "текст", Status.NEW, epicTwo.getId());

        fileBackedTasksManager.addSubTask(subTaskOne);
        fileBackedTasksManager.addSubTask(subTaskTwo);
        fileBackedTasksManager.addSubTask(subTaskThree);

        epicTwo.addSubtasks(subTaskOne);
        epicTwo.addSubtasks(subTaskTwo);
        epicTwo.addSubtasks(subTaskThree);

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println("2.2 Вывод всего списка задач Epic: ");
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println("2.3 Вывод всего списка задач Subtask: ");
        System.out.println(fileBackedTasksManager.getSubTasks());
        System.out.println("==========================================================");
        System.out.println(fileBackedTasksManager.getTaskById(1));
        System.out.println(fileBackedTasksManager.getTaskById(2));
        System.out.println("==========================================================getHistoryView");
        System.out.println(fileBackedTasksManager.getHistoryView());
        System.out.println("==========================================================getHistoryManager");
        System.out.println(fileBackedTasksManager.getHistoryManager());

        List<Task> list = fileBackedTasksManager.getHistoryManager().getHistory();

        for (Task list1: list) {
            System.out.println("GET ID = " + list1.getId());
        }

    }
}
