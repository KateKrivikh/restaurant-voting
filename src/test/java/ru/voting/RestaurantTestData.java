package ru.voting;

import ru.voting.model.Restaurant;

import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldByFieldAssertions(Restaurant.class);

    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_1_ID = START_SEQ + 2;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "Tkemali");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_1_ID + 1, "Megobari");

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1_ID, RESTAURANT_1.getName());
        updated.setName("UpdatedName");
        return updated;
    }
}