package service.exception;

/**
 * @author Дмитрий Карпушов 30.10.2022
 */
public class KVServerSaveException extends RuntimeException{
    public KVServerSaveException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}