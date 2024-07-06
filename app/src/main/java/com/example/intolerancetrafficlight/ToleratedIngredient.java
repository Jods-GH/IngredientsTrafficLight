package com.example.intolerancetrafficlight;

import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof ToleratedIngredient)){
            return false;
        }
        ToleratedIngredient other = (ToleratedIngredient) obj;
        if(this.getCiqual_food_code() !=0 && other.getCiqual_food_code() !=0 && this.getCiqual_food_code().equals(other.getCiqual_food_code()))
            return true;
        if(this.getNameString()!=null && other.getNameString() !=null &&  this.getNameString().toLowerCase().trim().equals(other.getNameString().toLowerCase().trim()))
            return true;
        return super.equals(obj);
    }
}
