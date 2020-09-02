package ru.graduation.voting;

import ru.graduation.voting.model.Vote;

import static ru.graduation.voting.DishTestData.DATE;
import static ru.graduation.voting.DishTestData.DATE_NOW;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_1_ID;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_2;
import static ru.graduation.voting.UserTestData.ADMIN_ID;
import static ru.graduation.voting.UserTestData.USER_ID;
import static ru.graduation.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldByFieldAssertions(Vote.class);

    public static final int NOT_FOUND = 10;
    public static final int VOTE_1_ID = START_SEQ + 10;

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, DATE, USER_ID, RESTAURANT_2.id());
    public static final Vote VOTE_2 = new Vote(VOTE_1_ID + 1, DATE, ADMIN_ID, RESTAURANT_2.id());

    public static Vote getNew() {
        return new Vote(null, DATE_NOW, USER_ID, RESTAURANT_2.id());
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE_1_ID, VOTE_1.getDate(), VOTE_1.getUserId(), VOTE_1.getRestaurantId());
        updated.setRestaurantId(RESTAURANT_1_ID);
        return updated;
    }
}