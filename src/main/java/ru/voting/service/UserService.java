package ru.voting.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.ValidationUtil.*;

@Service
public class UserService {
    private static final Logger log = getLogger(UserService.class);

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User create(User user) {
        checkNew(user);
        log.info("create {}", user);
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        log.info("update {} with id={}", user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }
}