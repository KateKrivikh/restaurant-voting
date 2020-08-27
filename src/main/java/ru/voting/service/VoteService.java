package ru.voting.service;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.Vote;
import ru.voting.repository.RestaurantRepository;
import ru.voting.repository.VoteRepository;
import ru.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.DateTimeUtil.BARRIER_TIME;
import static ru.voting.util.DateTimeUtil.ifNullThenNow;
import static ru.voting.util.ValidationUtil.*;

@Service
public class VoteService {
    private static final Logger log = getLogger(VoteService.class);

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Vote create(Vote vote) {
        checkNew(vote);
        log.info("create {}", vote);
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(restaurantRepository.get(vote.getRestaurantId()), vote.getRestaurantId());
        return repository.save(vote);
    }

    public void update(Vote vote, int id) {
        assureIdConsistent(vote, id);
        log.info("update {}", vote);
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(restaurantRepository.get(vote.getRestaurantId()), vote.getRestaurantId());
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

        try {
            Vote vote = getByUserAndDate(userId, date);
            checkTime(barrierTime);
            vote.setRestaurantId(restaurantId);
            update(vote, vote.id());
            return vote;
        } catch (NotFoundException e) {
            Vote vote = new Vote(null, date, userId, restaurantId);
            return create(vote);
        }
    }
}