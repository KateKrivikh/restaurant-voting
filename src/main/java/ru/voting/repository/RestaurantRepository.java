package ru.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {
    private final CrudRestaurantRepository crudRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<Restaurant> getAll() {
        return crudRepository.getAll();
    }

    public Restaurant get(int id) {
        return crudRepository.findById(id)
                .orElse(null);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }
}