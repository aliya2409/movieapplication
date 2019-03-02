package com.javalab.movieapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class User extends BaseEntity implements Serializable {
    public static final int USER_ROLE = 1;
    public static final int ADMIN_ROLE = 2;

    private String login;
    private String password;
    private int roleId;
    private String email;
    private LocalDate birthDate;


    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
