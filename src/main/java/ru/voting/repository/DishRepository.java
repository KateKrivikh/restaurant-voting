package ru.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {
    private final CrudDishRepository crudRepository;

    public DishRepository(CrudDishRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<Dish> getAll(int restaurantId, LocalDate date) {
        return crudRepository.getAll(restaurantId, date);
    }

    public Dish get(int id) {
        return crudRepository.findById(id)
                .orElse(null);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Transactional
    public Dish save(Dish dish) {
        return crudRepository.save(dish);
    }
}