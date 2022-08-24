import Manager.Managers;
import Manager.TaskManager;
import Test.Test;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Test test = new Test();
        test.test(manager);
    }
}
