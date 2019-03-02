package com.javalab.movieapp.validator;

import com.javalab.movieapp.entity.User;

public class UserValidator {

    public static boolean isntEmptyUser(User user) {
        boolean result;
        if (user.getLogin() != null && user.getEmail() != null && user.getPassword() != null && user.getBirthDate() != null && user.getRoleId() != 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
