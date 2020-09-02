package ru.graduation.voting.util.exception;

public class NotFoundException extends VoteUnprocessableException {
    public NotFoundException(String message) {
        super(message);
    }
}