package service.exception;

/**
 * @author Дмитрий Карпушов 29.10.2022
 */

public class KVTaskClientRegisterException extends RuntimeException{
    public KVTaskClientRegisterException(Throwable exception) {
        exception.printStackTrace();
    }
}
