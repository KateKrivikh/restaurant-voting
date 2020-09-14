package ru.graduation.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.voting.RestaurantTestData;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.service.VoteService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_1_ID;
import static ru.graduation.voting.RestaurantTestData.RESTAURANT_2;
import static ru.graduation.voting.TestUtil.userHttpBasic;
import static ru.graduation.voting.UserTestData.USER;
import static ru.graduation.voting.VoteTestData.VOTE_MATCHER;
import static ru.graduation.voting.VoteTestData.getNew;

class VoteRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    void voteFirstTime() throws Exception {
        Vote newVote = getNew();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        Vote created = service.getByUserAndDate(newVote.getUserId(), newVote.getDate());
        newVote.setId(created.id());
        VOTE_MATCHER.assertMatch(created, newVote);
    }

    @Test
    void voteFirstTimeRestaurantNotFound() throws Exception {
        Vote newVote = getNew();
        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND))
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void voteSecondTimeBeforeBarrier() throws Exception {
        Vote newVote = getNew();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        Vote created = service.getByUserAndDate(newVote.getUserId(), newVote.getDate());
        newVote.setId(created.id());

        newVote.setRestaurantId(RESTAURANT_1_ID);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        Vote updated = service.getByUserAndDate(newVote.getUserId(), newVote.getDate());

        VOTE_MATCHER.assertMatch(updated, newVote);
        VOTE_MATCHER.assertMatch(service.get(newVote.id()), newVote);
    }

    @Test
    void voteSecondTimeBeforeBarrierRestaurantNotFound() throws Exception {
        Vote newVote = getNew();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        Vote created = service.getByUserAndDate(newVote.getUserId(), newVote.getDate());
        newVote.setId(created.id());

        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND))
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID)))
                .andExpect(status().isUnauthorized());
    }
}