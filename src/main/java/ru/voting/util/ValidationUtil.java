package ru.voting.util;

import ru.voting.model.AbstractBaseEntity;
import ru.voting.util.exception.IllegalRequestDataException;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TooLateException;

import java.time.LocalTime;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String message) {
        checkNotFound(object != null, message);
        return object;
    }

    public static void checkNotFound(boolean found, String message) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + message);
        }
    }

    public static void checkNew(AbstractBaseEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkTime(LocalTime barrierTime) {
        if (LocalTime.now().isAfter(barrierTime))
            throw new TooLateException();
    }
}