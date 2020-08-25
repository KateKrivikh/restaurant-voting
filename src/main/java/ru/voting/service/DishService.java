package ru.voting.service;

import org.slf4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.DateUtil.ifNullThenNow;
import static ru.voting.util.ValidationUtil.*;

@Service
public class DishService {
    private static final Logger log = getLogger(DishService.class);

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAll(int restaurantId, @Nullable LocalDate date) {
        log.info("getAll");
        return repository.getAll(restaurantId, ifNullThenNow(date));
    }

    public Dish get(int id, int restaurantId) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish create(Dish dish, int restaurantId) {
        checkNew(dish);
        log.info("create {}", dish);
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        assureIdConsistent(dish, id);
        log.info("update {}", dish);
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.id());
    }
}