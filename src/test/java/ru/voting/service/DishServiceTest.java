package ru.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting.DishTestData.*;
import static ru.voting.RestaurantTestData.RESTAURANT_1_ID;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void getAll() {
        List<Dish> all = service.getAll(RESTAURANT_1_ID, DATE);
        DISH_MATCHER.assertMatch(all, DISH_1, DISH_3, DISH_2);
    }

    @Test
    void get() {
        Dish dish = service.get(DISH_1_ID);
        DISH_MATCHER.assertMatch(dish, DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(DISH_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void create() {
        Dish newDish = getNew();
        Dish created = service.create(newDish);

        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, updated.id());
        DISH_MATCHER.assertMatch(service.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Dish updated = getUpdated();
        updated.setId(NOT_FOUND);
        service.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> service.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Dish updated = getUpdated();
        assertThrows(IllegalArgumentException.class, () -> service.update(updated, NOT_FOUND));
    }
}