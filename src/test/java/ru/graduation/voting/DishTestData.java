package ru.graduation.voting;

import ru.graduation.voting.model.Dish;
import ru.graduation.voting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.RestaurantTestData.RESTAURANT_1;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_2;
import static ru.graduation.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "restaurant");
    public static final TestMatcher<MenuTo> MENU_MATCHER = TestMatcher.usingFieldByFieldAssertions(MenuTo.class);

    public static final int NOT_FOUND = 10;
    public static final int DISH_1_ID = START_SEQ + 4;

    public static final LocalDate DATE_TODAY = LocalDate.now();
    public static final LocalDate DATE = LocalDate.of(2020, 8, 20);

    public static final Dish DISH_1 = new Dish(DISH_1_ID, DATE_TODAY, "Chicken, lamb or veal kebab", 620, RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_1_ID + 1, DATE_TODAY, "Ojakhuri (roasted veal with potatoes served with tkemali sauce)", 650, RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_1_ID + 2, DATE_TODAY, "Khinkali with lamb", 320, RESTAURANT_1);
    public static final Dish DISH_4 = new Dish(DISH_1_ID + 3, DATE_TODAY, "Ajapsandali", 450, RESTAURANT_2);
    public static final Dish DISH_5 = new Dish(DISH_1_ID + 4, DATE_TODAY, "Chicken Satsivi", 470, RESTAURANT_2);
    public static final Dish DISH_6 = new Dish(DISH_1_ID + 5, DATE, "Eggplant roll with walnut and spices", 460, RESTAURANT_1);

    public static final List<MenuTo> MENU = List.of(
            new MenuTo(RESTAURANT_2, List.of(DISH_4, DISH_5)),
            new MenuTo(RESTAURANT_1, List.of(DISH_1, DISH_3, DISH_2)));
    public static final List<MenuTo> MENU_DATE = List.of(new MenuTo(RESTAURANT_1, List.of(DISH_6)));

    public static Dish getNew() {
        return new Dish(null, DATE_TODAY, "New", 500, RESTAURANT_1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1_ID, DISH_1.getDate(), DISH_1.getName(), DISH_1.getPrice(), RESTAURANT_1);
        updated.setName("UpdatedName");
        return updated;
    }
}