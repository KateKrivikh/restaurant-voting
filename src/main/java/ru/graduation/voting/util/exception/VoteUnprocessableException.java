package ru.graduation.voting.util.exception;

public class VoteUnprocessableException extends RuntimeException {
    public VoteUnprocessableException(String message) {
        super(message);
    }
}