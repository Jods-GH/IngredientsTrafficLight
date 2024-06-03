package com.example.intolerancetrafficlight;

import java.util.List;


public class FoodInfo {
    private String brand;
    private String Name;
    private Exception exception;
    private List<String> additives;
    private List<Ingredient> Ingredients;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Ingredient> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        Ingredients = ingredients;
    }

    public List<String> getAdditives() {
        return additives;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setAdditives(List<String> additives) {
        this.additives = additives;
    }
}
