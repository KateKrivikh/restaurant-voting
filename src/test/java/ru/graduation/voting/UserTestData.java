package ru.graduation.voting;

import ru.graduation.voting.model.Role;
import ru.graduation.voting.model.User;
import ru.graduation.voting.web.json.JsonUtil;

import java.util.Collections;

import static ru.graduation.voting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class, "password");

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final String USER_EMAIL = "user@gmail.com";

    public static final User USER = new User(USER_ID, "User", USER_EMAIL, "user", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER_ID, USER.getName(), USER.getEmail(), USER.getPassword(), USER.getRoles());
        updated.setName("UpdatedName");
        updated.setEmail("new_email@gmail.com");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}