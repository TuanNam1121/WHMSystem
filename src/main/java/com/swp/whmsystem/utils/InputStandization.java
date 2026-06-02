package com.swp.whmsystem.utils;

public class InputStandization {
    public static String validateName(String name) {
        String[] m = name.trim().split("\\s+");
        name = "";
        for (String str : m) {
            String head = str.substring(0, 1).toUpperCase();
            String tail = str.substring(1).toLowerCase();
            String result = head + tail + " ";
            name += result;
        }
        return name.trim();
    }

    public static boolean validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
