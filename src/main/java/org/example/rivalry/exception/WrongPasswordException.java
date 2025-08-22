package org.example.rivalry.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
