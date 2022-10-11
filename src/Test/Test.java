package Test;

import Enum.Status;
import Manager.TaskManager;
import model.*;
import service.FileBackedTasksManager;

import java.io.File;
import java.nio.file.Path;

public class Test {
    public void test(TaskManager FileBackedTasksManager) {
//        Path path = Path.of("test.csv");
//        File file = new File(String.valueOf(path));
//        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
//        //service.FileBackedTasksManager.createFiles(path);
//        //задачи
//        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ",Status.NEW);
//        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", Status.NEW);
//        fileBackedTasksManager.addTasks(taskOne);
//        fileBackedTasksManager.addTasks(taskTwo);
//        System.out.println("=======================================================================================================================");
//
//        Epic epicTwo = new Epic("Университет", "Написать диплом", Status.NEW);
//        fileBackedTasksManager.addEpic(epicTwo);
//
//        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", Status.IN_PROGRESS, 4);
//        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", Status.NEW, 4);
//        SubTask subTaskThree = new SubTask("Написать введение", "текст", Status.NEW, 4);
//
//        fileBackedTasksManager.addSubTask(subTaskOne);
//        fileBackedTasksManager.addSubTask(subTaskTwo);
//        fileBackedTasksManager.addSubTask(subTaskThree);
//
//        epicTwo.addSubtasks(subTaskOne);
//        epicTwo.addSubtasks(subTaskTwo);
//        epicTwo.addSubtasks(subTaskThree);
//
//        System.out.println("2.1 Вывод всего списка задач Task: ");
//        System.out.println(fileBackedTasksManager.getTasks());
//        System.out.println("2.2 Вывод всего списка задач Epic: ");
//        System.out.println(fileBackedTasksManager.getEpics());
//        System.out.println("2.3 Вывод всего списка задач Subtask: ");
//        System.out.println(fileBackedTasksManager.getSubTasks());
//        System.out.println("==========================================================");
//
//
//        System.out.println("1)Просмотр задач: ");
//        System.out.println(fileBackedTasksManager.getTaskById(1));
//        System.out.println(fileBackedTasksManager.getTaskById(2));
//        System.out.println(fileBackedTasksManager.getEpicById(3));
//        System.out.println(fileBackedTasksManager.getTaskById(1));
//        System.out.println(fileBackedTasksManager.getTaskById(2));
//        System.out.println("История просмотра после первого пункта");
//        System.out.println(fileBackedTasksManager.getHistoryView());
//        System.out.println("2)История просмотра задач: ");
//       // inMemoryTaskManager.deleteAllTask();
//       // inMemoryTaskManager.deleteSubtask();
//       // inMemoryTaskManager.deleteAllTask();
//       // FileBackedTasksManager.deleteTask(1);
//      //  inMemoryTaskManager.deleteEpic(3);
//        System.out.println(fileBackedTasksManager.getHistoryView());
//        System.out.println("=================================================");
//        System.out.println();

    }
}
