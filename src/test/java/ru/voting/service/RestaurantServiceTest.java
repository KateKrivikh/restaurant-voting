package ru.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.IllegalRequestDataException;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting.RestaurantTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHER.assertMatch(all, RESTAURANT_2, RESTAURANT_1);
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void create() {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(newRestaurant);

        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated, updated.id());
        RESTAURANT_MATCHER.assertMatch(service.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Restaurant updated = getUpdated();
        updated.setId(NOT_FOUND);
        service.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> service.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Restaurant updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, RESTAURANT_2.id()));
    }
}