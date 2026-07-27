package com.swp.whmsystem.utils;

public class InputValidationUtil {
    private static final int MAX_PASSWORD_LENGTH = 72;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

    private static final String PHONE_REGEX = "^0[0-9]{9}$";

    private static final String FIRST_NAME_REGEX = "^[\\p{L}]+(?:\\s+[\\p{L}]+)*$";

    private static final String LAST_NAME_REGEX = "^[\\p{L}]+$";

    public static String normalizeSearchText(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        if (trimmed.length() > maxLength) {
            return trimmed.substring(0, maxLength);
        }
        return trimmed;
    }

    public static boolean isValidPassword(String password) {

        boolean hasUppercase = false;
        boolean hasNumber = false;

        if (password == null || password.length() < 6 || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isWhitespace(ch)) {
                return false;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }

        }

        return hasUppercase && hasNumber;
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        return email.trim().matches(EMAIL_REGEX);
    }

    public static boolean isPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;

        return phone.trim().matches(PHONE_REGEX);
    }

    public static boolean isFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) return false;

        return firstName.trim().matches(FIRST_NAME_REGEX);
    }

    public static boolean isLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) return false;

        return lastName.trim().matches(LAST_NAME_REGEX);
    }
}
