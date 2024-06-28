package com.example.intolerancetrafficlight;

import java.util.List;

public class Intolerance {
    List<ToleratedIngredient> toleratedIngredients;
    List<ToleratedIngredient> intoleratedIngredients;

    IntoleranceEnum intolerance;

    Intolerance(IntoleranceEnum intolerance, List<ToleratedIngredient> intoleratedIngredients, List<ToleratedIngredient> toleratedIngredients){
        this.intolerance = intolerance;
        this.toleratedIngredients = toleratedIngredients;
        this.intoleratedIngredients = intoleratedIngredients;
    }


    public List<ToleratedIngredient> getToleratedIngredients() {
        return toleratedIngredients;
    }

    public void setToleratedIngredients(List<ToleratedIngredient> toleratedIngredients) {
        this.toleratedIngredients = toleratedIngredients;
    }

    public List<ToleratedIngredient> getIntoleratedIngredients() {
        return intoleratedIngredients;
    }

    public void setIntoleratedIngredients(List<ToleratedIngredient> intoleratedIngredients) {
        this.intoleratedIngredients = intoleratedIngredients;
    }

    public IntoleranceEnum getIntolerance() {
        return intolerance;
    }

    public void setIntolerance(IntoleranceEnum intolerance) {
        this.intolerance = intolerance;
    }
}
