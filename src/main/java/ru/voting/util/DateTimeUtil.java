package ru.voting.util;

import ru.voting.util.exception.ApplicationError;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class DateTimeUtil {
    public static final LocalTime BARRIER_TIME = getBarrierTime();

    public static LocalDate ifNullThenNow(LocalDate localDate) {
        return localDate != null ? localDate : LocalDate.now();
    }

    private static LocalTime getBarrierTime() {
        try (InputStream input = DateTimeUtil.class.getClassLoader().getResourceAsStream("time.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            int hours = Integer.parseInt(properties.getProperty("time.hours"));
            int minutes = Integer.parseInt(properties.getProperty("time.minutes"));

            return LocalTime.of(hours, minutes);
        } catch (NumberFormatException e) {
            throw new ApplicationError("Incorrect time in config file");
        } catch (IOException e) {
            throw new ApplicationError("Error while reading config file");
        } catch (Exception e) {
            throw new ApplicationError("Unknown error while reading config file");
        }
    }
}