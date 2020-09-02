package ru.graduation.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.to.MenuTo;

import java.util.List;

import static ru.graduation.voting.DishTestData.*;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void getMenuToday() {
        List<MenuTo> actual = service.getMenu(null);
        MENU_MATCHER.assertMatch(actual, List.of());
    }

    @Test
    void getMenuByDate() {
        List<MenuTo> actual = service.getMenu(DATE);
        MENU_MATCHER.assertMatch(actual, MENU);
    }
}