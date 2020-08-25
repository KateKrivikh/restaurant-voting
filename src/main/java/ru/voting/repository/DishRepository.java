package ru.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {
    private final CrudDishRepository crudRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DishRepository(CrudDishRepository crudRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public List<Dish> getAll(int restaurantId, LocalDate date) {
        return crudRepository.getAll(restaurantId, date);
    }

    public Dish get(int id, int restaurantId) {
        return crudRepository.getById(id, restaurantId);
    }

    public boolean delete(int id, int restaurantId) {
        return crudRepository.delete(id, restaurantId) != 0;
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudRepository.save(dish);
    }
}