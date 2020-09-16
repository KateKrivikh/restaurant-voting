package ru.graduation.voting.util;

import java.time.LocalDate;

public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static LocalDate ifNullThenNow(LocalDate localDate) {
        return localDate != null ? localDate : LocalDate.now();
    }
}