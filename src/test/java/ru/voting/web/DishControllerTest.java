package ru.voting.web;

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
class DishControllerTest {

    @Autowired
    private DishController controller;

    @Test
    void getAll() {
        List<Dish> all = controller.getAll(RESTAURANT_1_ID, DATE);
        assertMatch(all, List.of(DISH_1, DISH_3, DISH_2));
    }

    @Test
    void get() {
        Dish dish = controller.get(DISH_1_ID);
        assertMatch(dish, DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(DISH_NOT_FOUND));
    }

    @Test
    void delete() {
        controller.delete(DISH_1_ID);
        assertThrows(NotFoundException.class, () -> controller.get(DISH_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(DISH_NOT_FOUND));
    }

    @Test
    void create() {
        Dish newDish = getNew();
        Dish created = controller.create(newDish);

        int newId = created.id();
        newDish.setId(newId);
        assertMatch(created, newDish);
        assertMatch(controller.get(newId), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        controller.update(updated, updated.id());
        assertMatch(controller.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Dish updated = getUpdated();
        updated.setId(DISH_NOT_FOUND);
        controller.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> controller.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Dish updated = getUpdated();
        assertThrows(IllegalArgumentException.class, () -> controller.update(updated, DISH_NOT_FOUND));
    }
}