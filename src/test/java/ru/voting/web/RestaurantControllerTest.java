package ru.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting.RestaurantTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
class RestaurantControllerTest {

    @Autowired
    private RestaurantController controller;

    @Test
    void getAll() {
        List<Restaurant> all = controller.getAll();
        assertMatch(all, List.of(RESTAURANT_2, RESTAURANT_1));
    }

    @Test
    void get() {
        Restaurant restaurant = controller.get(RESTAURANT_1_ID);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(RESTAURANT_NOT_FOUND));
    }

    @Test
    void delete() {
        controller.delete(RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () -> controller.get(RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(RESTAURANT_NOT_FOUND));
    }

    @Test
    void create() {
        Restaurant newRestaurant = getNew();
        Restaurant created = controller.create(newRestaurant);

        int newId = created.id();
        newRestaurant.setId(newId);
        assertMatch(created, newRestaurant);
        assertMatch(controller.get(newId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        controller.update(updated, updated.id());
        assertMatch(controller.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Restaurant updated = getUpdated();
        updated.setId(RESTAURANT_NOT_FOUND);
        controller.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> controller.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Restaurant updated = getUpdated();
        assertThrows(IllegalArgumentException.class, () -> controller.update(updated, RESTAURANT_NOT_FOUND));
    }
}