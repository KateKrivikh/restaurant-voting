package ru.graduation.voting.util;

import org.slf4j.Logger;
import ru.graduation.voting.util.exception.ApplicationError;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public final class DateTimeUtil {
    private static final Logger LOG = getLogger(DateTimeUtil.class);
    public static final LocalTime BARRIER_TIME = getBarrierTime();

    private DateTimeUtil() {
    }

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
            LOG.error("Incorrect time in config file: time.properties", e);
            throw new ApplicationError("Incorrect time in config file");
        } catch (IOException e) {
            LOG.error("Error while reading config file: time.properties", e);
            throw new ApplicationError("Error while reading config file");
        } catch (Exception e) {
            LOG.error("Unknown error while reading config file: time.properties", e);
            throw new ApplicationError("Unknown error while reading config file");
        }
    }
}