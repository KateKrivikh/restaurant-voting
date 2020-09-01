package ru.voting.util.exception;

public class VoteUnprocessableException extends RuntimeException {
    public VoteUnprocessableException(String message) {
        super(message);
    }
}