package ru.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  http://stackoverflow.com/a/22358422/548473
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Incorrect data")  // 422
public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String message) {
        super(message);
    }
}