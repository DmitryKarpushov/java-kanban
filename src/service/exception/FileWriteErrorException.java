package service.exception;

/**
 * @author Дмитрий Карпушов 13.10.2022
 */
public class FileWriteErrorException extends RuntimeException{
    public FileWriteErrorException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}

