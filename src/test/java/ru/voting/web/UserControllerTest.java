package ru.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.User;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting.UserTestData.*;


@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
class UserControllerTest {

    @Autowired
    private UserController controller;

    @Test
    void getAll() {
        List<User> all = controller.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void get() {
        User user = controller.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = controller.getByEmail(USER_EMAIL);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> controller.getByEmail("something"));
    }

    @Test
    void delete() {
        controller.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }

    @Test
    void create() {
        User newUser = getNew();
        User created = controller.create(newUser);

        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    void update() {
        User updated = getUpdated();
        controller.update(updated, updated.id());
        USER_MATCHER.assertMatch(controller.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        User updated = getUpdated();
        updated.setId(NOT_FOUND);
        controller.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> controller.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        User updated = getUpdated();
        assertThrows(IllegalArgumentException.class, () -> controller.update(updated, NOT_FOUND));
    }
}