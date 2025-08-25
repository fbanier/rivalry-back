package org.example.rivalry.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException() {
        super("User Already Exist");
    }
}
