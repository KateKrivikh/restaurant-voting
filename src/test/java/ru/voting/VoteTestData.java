package ru.voting;

import ru.voting.model.Vote;

import static ru.voting.DishTestData.DATE;
import static ru.voting.RestaurantTestData.RESTAURANT_1;
import static ru.voting.RestaurantTestData.RESTAURANT_2;
import static ru.voting.UserTestData.ADMIN;
import static ru.voting.UserTestData.USER;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldByFieldAssertions(Vote.class);

    public static final int NOT_FOUND = 10;
    public static final int VOTE_1_ID = START_SEQ + 10;

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, DATE, USER, RESTAURANT_2);
    public static final Vote VOTE_2 = new Vote(VOTE_1_ID + 1, DATE, ADMIN, RESTAURANT_2);

    public static Vote getNew() {
        return new Vote(null, DATE.plusDays(1), USER, RESTAURANT_2);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE_1_ID, VOTE_1.getDate(), VOTE_1.getUser(), VOTE_1.getRestaurant());
        updated.setRestaurant(RESTAURANT_1);
        return updated;
    }
}