public class Test {
    public void test(TaskManager inMemoryTaskManager) {
        //задачи
        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ", Status.NEW);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", Status.NEW);
        inMemoryTaskManager.addTasks(taskOne);
        inMemoryTaskManager.addTasks(taskTwo);
        System.out.println("=======================================================================================================================");

        Epic epicTwo = new Epic("Университет", "Написать диплом", Status.NEW);
        inMemoryTaskManager.addEpic(epicTwo);

        SubTask subTaskOne = new SubTask("Написать вывод", "вывод", Status.IN_PROGRESS, 4);
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", Status.NEW, 4);
        SubTask subTaskThree = new SubTask("Написать введение", "текст", Status.NEW, 4);

        inMemoryTaskManager.addSubTask(subTaskOne);
        inMemoryTaskManager.addSubTask(subTaskTwo);
        inMemoryTaskManager.addSubTask(subTaskThree);

        epicTwo.addSubtasks(subTaskOne);
        epicTwo.addSubtasks(subTaskTwo);
        epicTwo.addSubtasks(subTaskThree);

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println("2.2 Вывод всего списка задач Epic: ");
        System.out.println(inMemoryTaskManager.getAllTaskEpic());
        System.out.println("2.3 Вывод всего списка задач Subtask: ");
        System.out.println(inMemoryTaskManager.getAllSubTask());
        System.out.println("==========================================================");


        System.out.println("1)Просмотр задач: ");
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        System.out.println(inMemoryTaskManager.getHistoryManager());
        System.out.println("2)Просмотр задач: ");
       // inMemoryTaskManager.deleteAllTask();
        //inMemoryTaskManager.deleteAllSubtask();
        inMemoryTaskManager.deleteAllTask();
       // inMemoryTaskManager.deleteTask(1);
      //  inMemoryTaskManager.deleteEpic(3);
        System.out.println();
        System.out.println();
        System.out.println(inMemoryTaskManager.getHistoryManager());
        System.out.println("=================================================");

    }
}
