package com.swp.whmsystem.utils;

public class InputValidationUtil {

    public static boolean isValidPassword(String password) {

        boolean hasUppercase = false;
        boolean hasNumber = false;

        if (password == null || password.length() < 6 || password.contains(" ")) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (hasUppercase && hasNumber) {
                return true;
            }
        }

        return false;
    }
}

