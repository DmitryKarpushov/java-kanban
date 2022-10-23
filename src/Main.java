import model.Epic;
import model.SubTask;
import model.Task;
import service.filedao.FileBackedTasksManager;
import enums.*;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    public static void main(String[] args) {

        Path path = Path.of("test.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        Task taskOne = new Task("Уборка", "Погладить вещи", TaskStatus.NEW, LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 22), 5);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе", TaskStatus.NEW, LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 45), 5);
        fileBackedTasksManager.addTasks(taskOne);
        fileBackedTasksManager.addTasks(taskTwo);

        Epic epicTwo = new Epic("Университет", "Написать диплом", TaskStatus.NEW,null,0,null);
        fileBackedTasksManager.addEpic(epicTwo);

        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", TaskStatus.NEW, epicTwo.getId(),LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 30), 5);
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", TaskStatus.IN_PROGRESS, epicTwo.getId(),LocalDateTime.of(2022, Month.FEBRUARY, 2, 22, 36), 5);
        SubTask subTaskThree = new SubTask("Написать реализацию", "код", TaskStatus.NEW, epicTwo.getId(),LocalDateTime.of(2032, Month.FEBRUARY, 2, 22, 36), 5);
        SubTask subTaskFour = new SubTask("Написать реализацию", "код", TaskStatus.DONE, epicTwo.getId(),LocalDateTime.of(2031, Month.FEBRUARY, 2, 22, 36), 5);

        fileBackedTasksManager.addSubTask(subTaskOne);
        fileBackedTasksManager.addSubTask(subTaskTwo);
        fileBackedTasksManager.addSubTask(subTaskThree);
        fileBackedTasksManager.addSubTask(subTaskFour);

        epicTwo.addSubtasks(subTaskOne);
        epicTwo.addSubtasks(subTaskTwo);
        epicTwo.addSubtasks(subTaskThree);
        epicTwo.addSubtasks(subTaskFour);
        System.out.println(fileBackedTasksManager.getEpics());

    }
}
