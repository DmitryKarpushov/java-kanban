package service.exception;

/**
 * @author Дмитрий Карпушов 29.10.2022
 */
public class KVTaskClientPutException extends RuntimeException{
    public KVTaskClientPutException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}
