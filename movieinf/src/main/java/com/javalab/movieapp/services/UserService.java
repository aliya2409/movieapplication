package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
public interface UserService {
    void update(UserDto user);

    UserDto findUserByEmail(String email);

    void create(UserDto user);

    void delete(Long userId);

    List<UserDto> findAll();

    UserDto findUser(String email, String password);

    UserDto findEntityById(Long userId);

    void changePassword(Long userId, String newPassword);

    void changeUserInfo(Long userId, String newLogin, LocalDate newBirthDate);
}
