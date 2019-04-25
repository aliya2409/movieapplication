package com.javalab.movieapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    private String login;
    private String password;
    private int roleId;
    private String email;
    private LocalDate birthDate;
}
