package com.lohika.itkachuk.javatc.lesson3.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: itkachuk
 * Date: 3/15/13 11:44 AM
 */
public class FileSearcherException extends Exception {

    public FileSearcherException() {
        super();
    }

    public FileSearcherException(String message) {
        super(message);
    }

    public FileSearcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
