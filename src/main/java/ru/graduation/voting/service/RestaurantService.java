package ru.graduation.voting.service;

import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.CrudRestaurantRepository;
import ru.graduation.voting.util.exception.NotFoundException;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.ValidationUtil.*;

@Service
public class RestaurantService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final Logger log = getLogger(RestaurantService.class);

    private final CrudRestaurantRepository repository;

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(SORT_NAME);
    }

    @Cacheable(value = "restaurant", key = "#id")
    public Restaurant get(int id) {
        log.info("get {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found restaurant with id=" + id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "restaurant", key = "#id"),
                    @CacheEvict(value = "menu", allEntries = true)
            }
    )
    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "restaurant", key = "#id"),
                    @CacheEvict(value = "menu", allEntries = true)
            }
    )
    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        log.info("update {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }
}