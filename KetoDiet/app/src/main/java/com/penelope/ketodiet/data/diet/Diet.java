package com.penelope.ketodiet.data.diet;

import java.util.Objects;

public class Diet {

    private String name;
    private double carbohydrates;
    private double protein;
    private double fat;
    private long created;

    public Diet() {
    }

    public Diet(String name, double carbohydrates, double protein, double fat) {
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.created = System.currentTimeMillis();
    }

    public String getName() {
        return name;
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

    public double getCalories() {
        return (carbohydrates * 4 + protein * 4 + fat * 9);
    }

    public long getCreated() {
        return created;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diet diet = (Diet) o;
        return Double.compare(diet.carbohydrates, carbohydrates) == 0 && Double.compare(diet.protein, protein) == 0 && Double.compare(diet.fat, fat) == 0 && created == diet.created && name.equals(diet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, carbohydrates, protein, fat, created);
    }
}
