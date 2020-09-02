package ru.voting.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.voting.View;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@MappedSuperclass
public class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 150)
    @SafeHtml(groups = {View.Rest.class}, whitelistType = NONE)
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}
