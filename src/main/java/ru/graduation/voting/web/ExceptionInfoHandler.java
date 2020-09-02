package ru.graduation.voting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.graduation.voting.util.ValidationUtil;
import ru.graduation.voting.util.exception.ErrorInfo;
import ru.graduation.voting.util.exception.ErrorType;
import ru.graduation.voting.util.exception.VoteUnprocessableException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static ru.graduation.voting.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_USER_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_RESTAURANT_NAME = "Restaurant with this name already exists";
    public static final String EXCEPTION_DUPLICATE_DISH = "Dish with this name for this restaurant on this date already exists";
    public static final String EXCEPTION_DUPLICATE_VOTE = "User is already voted for this date";

    private static final Map<String, String> CONSTRAINS_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_USER_EMAIL,
            "restaurants_unique_name_idx", EXCEPTION_DUPLICATE_RESTAURANT_NAME,
            "dishes_unique_date_restaurant_name_idx", EXCEPTION_DUPLICATE_DISH,
            "votes_unique_date_user_idx", EXCEPTION_DUPLICATE_VOTE);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINS_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, entry.getValue());
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(VoteUnprocessableException.class)
    public ErrorInfo handleError(HttpServletRequest req, VoteUnprocessableException e) {
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType,
                details.length != 0 ? details : new String[]{rootCause.getMessage()});
    }
}