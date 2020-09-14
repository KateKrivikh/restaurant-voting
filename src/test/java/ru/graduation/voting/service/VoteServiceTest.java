package ru.graduation.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.graduation.voting.RestaurantTestData;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.util.exception.IllegalRequestDataException;
import ru.graduation.voting.util.exception.NotFoundException;
import ru.graduation.voting.util.exception.TooLateException;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.voting.DishTestData.DATE;
import static ru.graduation.voting.DishTestData.DATE_TODAY;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_1_ID;
import static ru.graduation.voting.UserTestData.USER_ID;
import static ru.graduation.voting.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void get() {
        Vote vote = service.get(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = service.create(newVote);

        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void createRestaurantNotFound() {
        Vote newVote = getNew();
        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.create(newVote));
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.update(updated, updated.id());
        VOTE_MATCHER.assertMatch(service.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Vote updated = getUpdated();
        updated.setId(NOT_FOUND);
        service.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> service.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Vote updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, NOT_FOUND));
    }

    @Test
    void updateRestaurantNotFound() {
        Vote updated = getUpdated();
        updated.setRestaurantId(RestaurantTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, updated.id()));
    }

    @Test
    void getByUserAndDate() {
        Vote actual = service.getByUserAndDate(USER_ID, DATE);
        VOTE_MATCHER.assertMatch(actual, VOTE_1);
    }

    @Test
    void getByUserAndDateNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByUserAndDate(USER_ID, DATE_TODAY));
    }

    @Test
    void voteFirstTime() {
        Vote newVote = getNew();
        Vote voted = service.vote(USER_ID, newVote.getRestaurantId());

        int newId = voted.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(voted, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void voteFirstTimeRestaurantNotFound() {
        Vote newVote = getNew();
        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.vote(USER_ID, newVote.getRestaurantId()));
    }

    @Test
    void voteSecondTimeBeforeBarrier() {
        // first time
        Vote newVote = getNew();
        Vote voted = service.vote(USER_ID, newVote.getRestaurantId());
        int newId = voted.id();
        newVote.setId(newId);

        // second time: restaurant is changed
        newVote.setRestaurantId(RESTAURANT_1_ID);
        voted = service.vote(USER_ID, newVote.getRestaurantId());

        VOTE_MATCHER.assertMatch(voted, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void voteSecondTimeBeforeBarrierRestaurantNotFound() {
        // first time
        Vote newVote = getNew();
        Vote voted = service.vote(USER_ID, newVote.getRestaurantId());
        int newId = voted.id();
        newVote.setId(newId);

        // second time: restaurant is unknown
        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.vote(USER_ID, newVote.getRestaurantId()));
    }

    @Test
    void voteSecondTimeAfterBarrier() {
        // first time
        Vote newVote = getNew();
        Vote voted = service.vote(USER_ID, newVote.getRestaurantId());
        int newId = voted.id();
        newVote.setId(newId);

        // second time: restaurant is changed
        newVote.setRestaurantId(RESTAURANT_1_ID);
        assertThrows(TooLateException.class, () -> service.vote(USER_ID, newVote.getRestaurantId(), LocalTime.of(0, 0)));
    }
}