package com.penelope.ketodiet.utils;

import java.util.Locale;

public class AuthUtils {

    public static String emailize(String id) {
        return String.format(Locale.getDefault(), "%s@ketodiet.com", id);
    }

}
