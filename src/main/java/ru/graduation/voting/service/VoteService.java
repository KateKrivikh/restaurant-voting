package ru.graduation.voting.service;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.repository.CrudVoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.DateTimeUtil.BARRIER_TIME;
import static ru.graduation.voting.util.DateTimeUtil.ifNullThenNow;
import static ru.graduation.voting.util.ValidationUtil.*;

@Service
public class VoteService {
    private final Logger log = getLogger(VoteService.class);

    private final CrudVoteRepository repository;
    private final RestaurantService restaurantService;

    public VoteService(CrudVoteRepository repository, RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public Vote get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.getById(id), id);
    }

    @Transactional
    public Vote create(Vote vote) {
        checkNew(vote);
        log.info("create {}", vote);
        Assert.notNull(vote, "vote must not be null");
        // check if restaurant exists
        restaurantService.get(vote.getRestaurantId());
        return repository.save(vote);
    }

    @Transactional
    public void update(Vote vote, int id) {
        assureIdConsistent(vote, id);
        log.info("update {}", vote);
        Assert.notNull(vote, "vote must not be null");
        // check if restaurant exists
        restaurantService.get(vote.getRestaurantId());
        checkNotFoundWithId(repository.save(vote), vote.id());
    }

    public Vote getByUserAndDate(int userId, LocalDate date) {
        log.info("get by userId {} on date {}", userId, date);
        return checkNotFound(repository.getByUserAndDate(userId, date),
                "vote is not found by userId = " + userId + " and date = " + date);
    }

    public Vote vote(int userId, int restaurantId) {
        return vote(userId, restaurantId, BARRIER_TIME);
    }

    public Vote vote(int userId, int restaurantId, @NotNull LocalTime barrierTime) {
        LocalDate date = ifNullThenNow(null);
        log.info("vote user {} on date {} for restaurant {}", userId, date, restaurantId);

        Vote vote = repository.getByUserAndDate(userId, date);
        if (vote == null) {
            vote = new Vote(null, date, userId, restaurantId);
            return create(vote);
        } else {
            checkTime(barrierTime);
            vote.setRestaurantId(restaurantId);
            update(vote, vote.id());
            return vote;
        }
    }
}