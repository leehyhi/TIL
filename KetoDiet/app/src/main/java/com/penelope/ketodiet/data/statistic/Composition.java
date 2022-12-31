package com.penelope.ketodiet.data.statistic;

import com.penelope.ketodiet.data.diet.Diet;

import java.util.List;

public class Composition {

    private final double carbohydrates;
    private final double protein;
    private final double fat;

    public Composition(double carbohydrates, double protein, double fat) {
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
    }

    public static Composition fromDiets(List<Diet> diets) {
        double carbohydrates = 0;
        double proteins = 0;
        double fats = 0;
        for (Diet diet : diets) {
            carbohydrates += diet.getCarbohydrates();
            proteins += diet.getProtein();
            fats += diet.getFat();
        }
        return new Composition(carbohydrates, proteins, fats);
    }

    public static Composition fromStatistics(List<Statistic> statistics) {
        double carbohydrates = 0;
        double protein = 0;
        double fat = 0;
        for (Statistic statistic : statistics) {
            carbohydrates += statistic.getCarbohydrates();
            protein += statistic.getProtein();
            fat += statistic.getFat();
        }
        return new Composition(carbohydrates, protein, fat);
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

    public double getCPF() {
        return (carbohydrates + protein + fat);
    }

    public double getRateOfCarbohydrate() {
        return getCPF() > 0 ? carbohydrates / getCPF() : 0;
    }

    public double getRateOfProtein() {
        return getCPF() > 0 ? protein / getCPF() : 0;
    }

    public double getRateOfFats() {
        return getCPF() > 0 ? fat / getCPF() : 0;
    }

}
