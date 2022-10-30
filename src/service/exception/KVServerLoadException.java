package service.exception;

/**
 * @author Дмитрий Карпушов 30.10.2022
 */
public class KVServerLoadException extends RuntimeException{
    public KVServerLoadException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}