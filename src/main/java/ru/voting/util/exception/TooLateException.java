package ru.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = TooLateException.RESTAURANT_ALREADY_CHOSEN)  // 422
public class TooLateException extends RuntimeException {

    public static final String RESTAURANT_ALREADY_CHOSEN = "Restaurant is already been chosen";

    public TooLateException() {
        super(RESTAURANT_ALREADY_CHOSEN);
    }
}