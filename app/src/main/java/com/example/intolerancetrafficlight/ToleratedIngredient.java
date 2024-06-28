package com.example.intolerancetrafficlight;

public class ToleratedIngredient {
    private String nameString;
    private Integer ciqual_food_code;

    private boolean isSave;


    public ToleratedIngredient(String nameString, Integer ciqual_food_code, Boolean isSave) {
        this.nameString = nameString;
        this.ciqual_food_code = ciqual_food_code;
        this.isSave = isSave;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public Integer getCiqual_food_code() {
        return ciqual_food_code;
    }

    public void setCiqual_food_code(Integer ciqual_food_code) {
        this.ciqual_food_code = ciqual_food_code;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }
}
