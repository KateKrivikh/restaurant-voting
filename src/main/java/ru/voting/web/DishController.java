package ru.voting.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.ValidationUtil.*;

@Controller
public class DishController {
    private static final Logger log = getLogger(DishController.class);

    private final DishRepository repository;

    public DishController(DishRepository repository) {
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