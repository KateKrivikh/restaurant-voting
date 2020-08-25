package ru.voting.util;

import java.time.LocalDate;

public class DateUtil {
    public static LocalDate ifNullThenNow(LocalDate localDate) {
        return localDate != null ? localDate : LocalDate.now();
    }
}