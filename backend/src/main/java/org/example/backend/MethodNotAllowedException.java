package org.example.backend;

public class MethodNotAllowedException extends Exception {
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
