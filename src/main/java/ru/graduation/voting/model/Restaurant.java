package ru.graduation.voting.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {
    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}