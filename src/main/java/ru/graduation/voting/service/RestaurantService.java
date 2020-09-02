package ru.graduation.voting.service;

import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.repository.RestaurantRepository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.ValidationUtil.*;

@Service
public class RestaurantService {
    private static final Logger log = getLogger(RestaurantService.class);

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    @Cacheable(value = "restaurant", key = "#id")
    public Restaurant get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "restaurant", key = "#id"),
                    @CacheEvict(value = "menu", allEntries = true)
            }
    )
    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

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