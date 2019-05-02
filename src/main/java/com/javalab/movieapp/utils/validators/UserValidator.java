package com.javalab.movieapp.utils.validators;

import com.javalab.movieapp.entities.User;

public class UserValidator {

    public static boolean isntEmptyUser(User user) {
        boolean result;
        result = user.getLogin() != null && user.getEmail() != null && user.getPassword() != null && user.getBirthDate() != null && user.getRoleId() != 0;
        return result;
    }
}
