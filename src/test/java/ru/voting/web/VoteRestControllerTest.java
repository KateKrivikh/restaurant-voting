package ru.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting.RestaurantTestData;
import ru.voting.model.Vote;
import ru.voting.service.VoteService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.RestaurantTestData.RESTAURANT_1_ID;
import static ru.voting.RestaurantTestData.RESTAURANT_2;
import static ru.voting.TestUtil.readFromJson;
import static ru.voting.VoteTestData.VOTE_MATCHER;
import static ru.voting.VoteTestData.getNew;

class VoteRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .contentType(MediaType.APPLICATION_JSON));

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void createRestaurantNotFound() throws Exception {
        Vote newVote = getNew();
        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateBeforeBarrier() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .contentType(MediaType.APPLICATION_JSON));
        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);

        newVote.setRestaurantId(RESTAURANT_1_ID);
        ResultActions actionUpdate = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .contentType(MediaType.APPLICATION_JSON));
        Vote updated = readFromJson(actionUpdate, Vote.class);

        VOTE_MATCHER.assertMatch(updated, newVote);
        VOTE_MATCHER.assertMatch(service.get(newVote.id()), newVote);
    }

    @Test
    void updateBeforeBarrierRestaurantNotFound() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_2.id()))
                .contentType(MediaType.APPLICATION_JSON));
        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);

        newVote.setRestaurantId(RestaurantTestData.NOT_FOUND);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}