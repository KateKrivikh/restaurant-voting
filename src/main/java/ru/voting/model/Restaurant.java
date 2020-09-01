package ru.voting.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.voting.View;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml(groups = {View.Rest.class}, whitelistType = NONE)
    private String name;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}