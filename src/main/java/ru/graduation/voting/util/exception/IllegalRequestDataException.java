package ru.graduation.voting.util.exception;

public class IllegalRequestDataException extends VoteUnprocessableException {
    public IllegalRequestDataException(String message) {
        super(message);
    }
}