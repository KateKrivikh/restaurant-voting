package ru.voting.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import ru.voting.model.Vote;
import ru.voting.repository.VoteRepository;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.ValidationUtil.*;

@Controller
public class VoteController {
    private static final Logger log = getLogger(VoteController.class);

    private VoteRepository repository;

    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Vote create(Vote vote) {
        checkNew(vote);
        log.info("create {}", vote);
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    public void update(Vote vote, int id) {
        assureIdConsistent(vote, id);
        log.info("update {}", vote);
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(repository.save(vote), vote.id());
    }
}