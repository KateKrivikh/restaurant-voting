package ru.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "You have already chosen restaurant")  // 422
public class TooLateException extends RuntimeException {
}