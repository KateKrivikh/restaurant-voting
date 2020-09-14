package ru.graduation.voting.model;

import ru.graduation.voting.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "vote_unique_date_user_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "user_id")
    @NotNull(groups = View.Persist.class)
    private int userId;

    @Column(name = "restaurant_id")
    @NotNull(groups = View.Persist.class)
    private int restaurantId;

    public Vote() {
    }

    public Vote(Integer id, LocalDate date, int userId, int restaurantId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}