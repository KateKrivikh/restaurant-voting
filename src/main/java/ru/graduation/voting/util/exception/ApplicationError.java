package ru.graduation.voting.util.exception;

public class ApplicationError extends RuntimeException {
    public ApplicationError(String message) {
        super(message);
    }
}