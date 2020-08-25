package ru.voting;

import ru.voting.model.Dish;

import java.time.LocalDate;

import static ru.voting.RestaurantTestData.RESTAURANT_1;
import static ru.voting.RestaurantTestData.RESTAURANT_2;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldByFieldAssertions(Dish.class);

    public static final int NOT_FOUND = 10;
    public static final int DISH_1_ID = START_SEQ + 4;

    public static final LocalDate DATE = LocalDate.of(2020, 8, 20);

    public static final Dish DISH_1 = new Dish(DISH_1_ID, DATE, "Chicken, lamb or veal kebab", 620, RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_1_ID + 1, DATE, "Ojakhuri (roasted veal with potatoes served with tkemali sauce)", 650, RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_1_ID + 2, DATE, "Khinkali with lamb", 320, RESTAURANT_1);
    public static final Dish DISH_4 = new Dish(DISH_1_ID + 3, DATE, "Ajapsandali", 450, RESTAURANT_2);
    public static final Dish DISH_5 = new Dish(DISH_1_ID + 4, DATE, "Chicken Satsivi", 470, RESTAURANT_2);
    public static final Dish DISH_6 = new Dish(DISH_1_ID + 5, DATE, "Eggplant roll with walnut and spices", 460, RESTAURANT_2);

    public static Dish getNew() {
        return new Dish(null, DATE, "New", 500, RESTAURANT_1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1_ID, DISH_1.getDate(), DISH_1.getName(), DISH_1.getPrice(), RESTAURANT_1);
        updated.setName("UpdatedName");
        return updated;
    }
}