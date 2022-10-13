package service.exception;

/**
 * @author Дмитрий Карпушов 12.10.2022
 * Исключения вида IOException нужно отлавливать внутри метода save и кидать собственное
 * непроверяемое исключение ManagerSaveException.
 * Благодаря этому можно не менять сигнатуру методов интерфейса менеджера.
 */
public class ManagerSaveException extends RuntimeException{
    public ManagerSaveException(String s) {
        System.out.println(s);
    }

}
