public class Test {
    public void test(TaskManager taskManager) {
        Task taskOne = new Task("Уборка", "Погладить вещи, постирать рубашки, помыть полы. ", "NEW",1);
        Task taskUpdate = new Task("Update.Уборка", "Update.Погладить вещи, постирать рубашки, помыть полы. ", "DONE",1);
        Task taskTwo = new Task("Прогулка", "Сходить в кафе, прогуляться по парку. ", "NEW",2);
        taskManager.addTasks(taskOne);
        taskManager.addTasks(taskTwo);
        System.out.println("=======================================================================================================================");


        Epic epicOne = new Epic("Поездка", "Москва", "NEW",3);
        Epic epicUpdate = new Epic("Update.Поездка", "Update.Москва", "DONE",3);
        Epic epicTwo = new Epic("Университет", "Написать диплом", "NEW",4);
        taskManager.addEpic(epicOne);
        taskManager.addEpic(epicTwo);


        SubTask subTaskOne = new SubTask("Сходить в театр", "Посетить самый популярный театр", "IN_PROGRESS", 5,"Поездка",3);
        SubTask subTaskTwo = new SubTask("Написать реализацию", "код", "NEW", 6,"Университет",4);
        SubTask subTaskUpdate = new SubTask("Update.Написать реализацию", "Update.код", "IN_PROGRESS", 6,"Университет",4);
        SubTask subTaskThree = new SubTask("Написать введение", "текст", "NEW", 7,"Университет",4);

        taskManager.addSubTask(subTaskOne);
        taskManager.addSubTask(subTaskTwo);
        taskManager.addSubTask(subTaskThree);

        epicOne.setEpicSubtasks(subTaskOne);
        epicTwo.setEpicSubtasks(subTaskTwo);
        epicTwo.setEpicSubtasks(subTaskThree);

        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(taskManager.printAllTask(taskManager.getMapOfTasks()));
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(taskManager.printAllTaskEpic(taskManager.getMapOfEpics()));
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(taskManager.printAllSubTask(taskManager.getMapOfSubTasks()));
        System.out.println("==========================================================");

        taskManager.updateTask(1,taskUpdate);
        taskManager.updateEpic(3,epicUpdate,taskManager.getEpicSubtasks(3));
        taskManager.updateSubtask(6,subTaskUpdate,4,subTaskTwo);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Проверка после обновления!");
        System.out.println("2.1 Вывод всего списка задач Task: ");
        System.out.println(taskManager.printAllTask(taskManager.getMapOfTasks()));
        System.out.println("2.1 Вывод всего списка задач Epic: ");
        System.out.println(taskManager.printAllTaskEpic(taskManager.getMapOfEpics()));
        System.out.println("2.1 Вывод всего списка задач Subtask: ");
        System.out.println(taskManager.printAllSubTask(taskManager.getMapOfSubTasks()));

    }
}
