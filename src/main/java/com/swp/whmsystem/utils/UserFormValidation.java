package com.swp.whmsystem.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class UserFormValidation {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,30}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9,15}$");
    
    private static final Set<String> ALLOWED_GENDERS = new HashSet<>(Arrays.asList(
            "MALE", "FEMALE", "OTHER"
    ));

    private UserFormValidation() {
    }

    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static Integer parsePositiveInt(String value) {
        String v = trimToNull(value);
        if (v == null) {
            return null;
        }
        try {
            int parsed = Integer.parseInt(v);
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidFullName(String fullName) {
        if (fullName == null) {
            return false;
        }
        String trimmed = fullName.trim();
        return trimmed.length() >= 2 && trimmed.length() <= 60;
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String trimmed = password.trim();
        return trimmed.length() >= 6 && trimmed.length() <= 72;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.length() <= 254;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidGender(String gender) {
        return gender != null && ALLOWED_GENDERS.contains(gender);
    }
}
