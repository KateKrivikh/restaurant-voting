package ru.graduation.voting.service;

import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.repository.CrudDishRepository;
import ru.graduation.voting.repository.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.DateTimeUtil.ifNullThenNow;
import static ru.graduation.voting.util.ValidationUtil.*;

@Service
public class DishService {
    private final Logger log = getLogger(DishService.class);

    private final CrudDishRepository repository;
    private final CrudRestaurantRepository restaurantRepository;

    public DishService(CrudDishRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Dish> getAll(int restaurantId, @Nullable LocalDate date) {
        log.info("getAll");
        return repository.getAllByRestaurantAndDate(restaurantId, ifNullThenNow(date));
    }

    public Dish get(int id, int restaurantId) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.getById(id, restaurantId), id);
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void delete(int id, int restaurantId) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    @Transactional
    @CacheEvict(value = "menu", key = "#dish.date")
    public Dish create(Dish dish, int restaurantId) {
        checkNew(dish);
        log.info("create {}", dish);
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return repository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "menu", key = "#dish.date")
    public void update(Dish dish, int id, int restaurantId) {
        assureIdConsistent(dish, id);
        log.info("update {}", dish);
        Assert.notNull(dish, "dish must not be null");
        // check dish belongs to restaurant
        get(dish.getId(), restaurantId);
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}