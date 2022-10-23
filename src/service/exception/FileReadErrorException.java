package service.exception;

/**
 * @author Дмитрий Карпушов 13.10.2022
 */
public class FileReadErrorException extends RuntimeException{
    public FileReadErrorException(String s, Throwable cause) {
        System.out.println(s);
        cause.printStackTrace();
    }

}