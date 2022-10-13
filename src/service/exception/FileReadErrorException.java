package service.exception;

/**
 * @author Дмитрий Карпушов 13.10.2022
 */
public class FileReadErrorException extends RuntimeException{
    public FileReadErrorException(String s) {
        System.out.println(s);
    }
}