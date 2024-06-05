package com.example.intolerancetrafficlight;

public class Intolerance {
    private String nameString;
    private Integer ciqual_food_code;


    public Intolerance(String nameString, Integer ciqual_food_code) {
        this.nameString = nameString;
        this.ciqual_food_code = ciqual_food_code;
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
