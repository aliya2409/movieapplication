package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void update(UserDto user) {

    }

    @Override
    public UserDto findUserByEmail(String email) {
        return null;
    }

    @Override
    public void create(UserDto user) {

    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public UserDto findUser(String email, String password) {
        return null;
    }

    @Override
    public UserDto findEntityById(Long userId) {
        return null;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {

    }

    @Override
    public void changeUserInfo(Long userId, String newLogin, LocalDate newBirthDate) {

    }
}
