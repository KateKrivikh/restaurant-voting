package ru.graduation.voting.util.exception;

public class TooLateException extends VoteUnprocessableException {

    public static final String RESTAURANT_ALREADY_CHOSEN = "Restaurant is already been chosen";

    public TooLateException() {
        super(RESTAURANT_ALREADY_CHOSEN);
    }
}