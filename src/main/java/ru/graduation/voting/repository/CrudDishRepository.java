package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.date = :date ORDER BY d.name ASC")
    List<Dish> getAllByRestaurantAndDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurantId")
    Dish getById(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = :date ORDER BY d.name ASC")
    List<Dish> getAllByDate(@Param("date") LocalDate date);
}