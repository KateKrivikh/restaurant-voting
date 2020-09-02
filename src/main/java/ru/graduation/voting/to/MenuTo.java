package ru.graduation.voting.to;

import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import java.util.List;
import java.util.Objects;

public class MenuTo {
    private Restaurant restaurant;
    private List<Dish> dishes;

    public MenuTo() {
    }

    public MenuTo(Restaurant restaurant, List<Dish> dishes) {
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuTo menuTo = (MenuTo) o;
        return restaurant.equals(menuTo.restaurant) &&
                dishes.equals(menuTo.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant, dishes);
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "restaurant=" + restaurant +
                ", dishes=" + dishes +
                '}';
    }
}