package ru.voting.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.ValidationUtil.*;

@Service
public class DishService {
    private static final Logger log = getLogger(DishService.class);

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAll(int restaurantId, LocalDate date) {
        log.info("getAll");
        return repository.getAll(restaurantId, date);
    }

    public Dish get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish create(Dish dish) {
        checkNew(dish);
        log.info("create {}", dish);
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    public void update(Dish dish, int id) {
        assureIdConsistent(dish, id);
        log.info("update {}", dish);
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}