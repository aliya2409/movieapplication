package com.javalab.movieapp.utils.validators;

import com.javalab.movieapp.dto.UserDto;

public class UserValidator {

    public static boolean isntEmptyUser(UserDto user) {
        boolean result;
        result = user.getLogin() != null && user.getEmail() != null && user.getPassword() != null && user.getBirthDate() != null && user.getRoleId() != 0;
        return result;
    }
}
