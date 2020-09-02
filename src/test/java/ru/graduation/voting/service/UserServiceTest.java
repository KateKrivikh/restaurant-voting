package ru.graduation.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.model.User;
import ru.graduation.voting.util.exception.IllegalRequestDataException;
import ru.graduation.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.voting.UserTestData.*;


class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void get() {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail(USER_EMAIL);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("something"));
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void create() {
        User newUser = getNew();
        User created = service.create(newUser);

        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated, updated.id());
        USER_MATCHER.assertMatch(service.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        User updated = getUpdated();
        updated.setId(NOT_FOUND);
        service.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> service.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        User updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, NOT_FOUND));
    }
}