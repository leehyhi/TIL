package com.penelope.ketodiet.res;

import com.penelope.ketodiet.data.MealTime;

import java.util.Locale;

public class StringRes {

    public static String mealTime(MealTime mealTime) {
        switch (mealTime) {
            case BREAKFAST: return "아침";
            case LUNCH: return "점심";
            case DINNER: return "저녁";
        }
        return null;
    }

    public static String calories(double value) {
        return String.format(Locale.getDefault(), "%.0fkcal", value);
    }

    public static String gram(double value) {
        return String.format(Locale.getDefault(), "%.1fg", value);
    }

    public static String monthDay(int month, int dayOfMonth) {
        return String.format(Locale.getDefault(), "%d.%02d", month, dayOfMonth);
    }

    public static String welcome(String name) {
        return String.format(Locale.getDefault(), "%s님, 안녕하세요.", name);
    }
}
