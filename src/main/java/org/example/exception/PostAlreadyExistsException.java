package org.example.exception;

public class PostAlreadyExistsException extends RuntimeException {
    public PostAlreadyExistsException(String message) {
       super(message);
    }
}
