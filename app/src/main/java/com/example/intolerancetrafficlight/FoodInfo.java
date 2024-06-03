package com.example.intolerancetrafficlight;

import java.util.List;


public class FoodInfo {
    String brand;
    String Name;

    List<String> additives;
    List<Ingredient> Ingredients;

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

    public void setAdditives(List<String> additives) {
        this.additives = additives;
    }
}
