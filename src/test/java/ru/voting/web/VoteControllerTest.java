package ru.voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Vote;
import ru.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.voting.VoteTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
class VoteControllerTest {

    @Autowired
    private VoteController controller;

    @Test
    void get() {
        Vote vote = controller.get(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = controller.create(newVote);

        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(controller.get(newId), newVote);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        controller.update(updated, updated.id());
        VOTE_MATCHER.assertMatch(controller.get(updated.id()), updated);
    }

    @Test
    void updateNotFound() {
        Vote updated = getUpdated();
        updated.setId(NOT_FOUND);
        controller.update(updated, updated.id());
        assertThrows(NotFoundException.class, () -> controller.get(updated.id()));
    }

    @Test
    void updateNotConsistentId() {
        Vote updated = getUpdated();
        assertThrows(IllegalArgumentException.class, () -> controller.update(updated, NOT_FOUND));
    }
}