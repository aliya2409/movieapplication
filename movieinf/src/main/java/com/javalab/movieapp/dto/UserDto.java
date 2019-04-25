package com.javalab.movieapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Getter
@Setter
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private int roleId;
    private String email;
    private LocalDate birthDate;
}
