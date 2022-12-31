package com.penelope.ketodiet.data.statistic;

import java.time.LocalDate;

public class Statistic {

    private String id;
    private int year;
    private int month;
    private int dayOfMonth;
    private double carbohydrates;
    private double protein;
    private double fat;
    private long created;

    public Statistic() {
    }

    public Statistic(double carbohydrates, double protein, double fat) {
        LocalDate now = LocalDate.now();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.dayOfMonth = now.getDayOfMonth();
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.created = System.currentTimeMillis();
        this.id = year + "_" + month + "_" + dayOfMonth;
    }

    public String getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public long getCreated() {
        return created;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
