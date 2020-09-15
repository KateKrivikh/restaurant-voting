package ru.graduation.voting.service;

import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.voting.AuthorizedUser;
import ru.graduation.voting.model.User;
import ru.graduation.voting.repository.CrudUserRepository;
import ru.graduation.voting.util.exception.NotFoundException;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.UserUtil.prepareToSave;
import static ru.graduation.voting.util.ValidationUtil.*;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");
    private final Logger log = getLogger(UserService.class);

    private final CrudUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(CrudUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable("users")
    public List<User> getAll() {
        log.info("getAll");
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id=" + id));
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        checkNew(user);
        log.info("create {}", user);
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user, int id) {
        assureIdConsistent(user, id);
        log.info("update {} with id={}", user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(prepareAndSave(user), user.id());
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("load user by email {}", email);
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }
}