package ru.voting.to;

import org.springframework.util.Assert;

import java.io.Serializable;

public class UserTo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;

    private String name;

    private String email;

    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}