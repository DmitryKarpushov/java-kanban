package service.exception;

/**
 * @author Дмитрий Карпушов 13.10.2022
 */
public class FileWriteErrorException extends RuntimeException{
    public FileWriteErrorException(String s) {
        System.out.println(s);
    }
}

