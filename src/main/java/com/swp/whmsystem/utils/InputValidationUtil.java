package com.swp.whmsystem.utils;

public class InputValidationUtil {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private static final String PHONE_REGEX = "^(0)+([0-9]{9})$";

    private static final String NAME_REGEX = "^[\\p{L}\\s]+$";

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

    public static boolean isEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        return email.trim().matches(EMAIL_REGEX);
    }

    public static boolean isPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;

        String cleanPhone = phone.replaceAll("[\\s.-]", "");
        return cleanPhone.matches(PHONE_REGEX);
    }

    public static boolean isName(String name) {
        if (name == null || name.trim().isEmpty()) return false;

        return name.trim().matches(NAME_REGEX);
    }
}

