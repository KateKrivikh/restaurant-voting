package ru.graduation.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.to.MenuTo;
import ru.graduation.voting.util.exception.IllegalRequestDataException;
import ru.graduation.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.voting.DishTestData.*;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_1_ID;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void getAllToday() {
        List<Dish> all = service.getAll(RESTAURANT_1_ID, null);
        DISH_MATCHER.assertMatch(all, DISH_1, DISH_3, DISH_2);
    }

    @Test
    void getAllByDate() {
        List<Dish> all = service.getAll(RESTAURANT_1_ID, DATE);
        DISH_MATCHER.assertMatch(all, DISH_6);
    }

    @Test
    void get() {
        Dish dish = service.get(DISH_1_ID, RESTAURANT_1_ID);
        DISH_MATCHER.assertMatch(dish, DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_1_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(DISH_4.id(), RESTAURANT_1_ID));
    }

    @Test
    void delete() {
        service.delete(DISH_1_ID, RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_4.id(), RESTAURANT_1_ID));
    }

    @Test
    void create() {
        Dish newDish = getNew();
        Dish created = service.create(newDish, RESTAURANT_1_ID);

        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, RESTAURANT_1_ID), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, updated.id(), RESTAURANT_1_ID);
        DISH_MATCHER.assertMatch(service.get(updated.id(), RESTAURANT_1_ID), updated);
    }

    @Test
    void updateNotFound() {
        Dish updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, updated.id(), RESTAURANT_1_ID));
    }

    @Test
    void updateNotConsistentId() {
        Dish updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, DISH_2.id(), RESTAURANT_1_ID));
    }

    @Test
    void updateNotOwn() {
        Dish updated = getUpdated();
        updated.setId(DISH_4.id());
        assertThrows(NotFoundException.class, () -> service.update(updated, updated.id(), RESTAURANT_1_ID));
    }

    @Test
    void getMenuByDate() {
        List<MenuTo> actual = service.getMenuByDate(DATE_TODAY);
        MENU_MATCHER.assertMatch(actual, MENU);
    }
}