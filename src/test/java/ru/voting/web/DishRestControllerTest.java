package ru.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;
import ru.voting.service.DishService;
import ru.voting.util.exception.NotFoundException;
import ru.voting.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.DishTestData.*;
import static ru.voting.RestaurantTestData.RESTAURANT_1;
import static ru.voting.RestaurantTestData.RESTAURANT_1_ID;
import static ru.voting.TestUtil.readFromJson;
import static ru.voting.TestUtil.userHttpBasic;
import static ru.voting.UserTestData.ADMIN;
import static ru.voting.UserTestData.USER;
import static ru.voting.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.voting.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH;

class DishRestControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishService service;

    @Test
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson());
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_1_ID)
                .param("date", "2020-08-20")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH_1, DISH_3, DISH_2));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH_1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_5.id(), RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_5.id(), RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(service.get(DISH_1_ID, RESTAURANT_1_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        Dish updated = getUpdated();
        updated.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL + NOT_FOUND, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotOwn() throws Exception {
        Dish updated = getUpdated();
        updated.setId(DISH_5.id());
        perform(MockMvcRequestBuilders.put(REST_URL + updated.id(), RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotConsistentId() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_5.id(), RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)));

        Dish created = readFromJson(action, Dish.class);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, RESTAURANT_1_ID), newDish);
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null, 0, RESTAURANT_1);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_1_ID, null, null, 0, RESTAURANT_1);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Dish duplicate = new Dish(null, DISH_1.getDate(), DISH_1.getName(), 100, RESTAURANT_1);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Dish duplicate = new Dish(DISH_2.id(), DISH_1.getDate(), DISH_1.getName(), 100, RESTAURANT_1);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_2.id(), RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH));
    }
}