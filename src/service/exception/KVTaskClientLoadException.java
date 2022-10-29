package service.exception;

/**
 * @author Дмитрий Карпушов 29.10.2022
 */
public class KVTaskClientLoadException extends RuntimeException{
    public KVTaskClientLoadException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}
