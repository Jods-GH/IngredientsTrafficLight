package com.example.intolerancetrafficlight;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {
    public static final String FOODCODE = "ciqual_food_code";
    private String foodCode;
    public static final String ID = "id";
    private String id;
    public static final String TAXONOMY = "is_in_taxonomy";
    private Integer inTaxonomy;
    public static final String PERCENT_ESTIMATE = "percent_estimate";
    private Double percentEstimate;
    public static final String PERCENT_MIN = "percent_min";
    private Double percentMin;
    public static final String PERCENT_MAX = "percent_max";
    private Double percentMax;
    public static final String PROCESSING = "processing";
    private String processing;
    public static final String TEXT = "text";
    private String text;
    public static final String VEGAN = "vegan";
    private Boolean vegan;
    public static final String VEGETARIAN = "vegetarian";
    private Boolean vegetarian;

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInTaxonomy() {
        return inTaxonomy;
    }

    public void setInTaxonomy(int inTaxonomy) {
        this.inTaxonomy = inTaxonomy;
    }

    public Double getPercentEstimate() {
        return percentEstimate;
    }

    public void setPercentEstimate(double percentEstimate) {
        this.percentEstimate = percentEstimate;
    }

    public Double getPercentMin() {
        return percentMin;
    }

    public void setPercentMin(double percentMin) {
        this.percentMin = percentMin;
    }

    public Double getPercentMax() {
        return percentMax;
    }

    public void setPercentMax(double percentMax) {
        this.percentMax = percentMax;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
    public Ingredient(JSONObject ingredient) throws JSONException {
        if(ingredient.has(ID))
            this.setId(ingredient.getString(ID));
        if(ingredient.has(FOODCODE))
            this.setFoodCode(ingredient.getString(FOODCODE));
        if(ingredient.has(TAXONOMY))
            this.setInTaxonomy(ingredient.getInt(TAXONOMY));
        if(ingredient.has(PERCENT_ESTIMATE))
            this.setPercentEstimate(ingredient.getInt(PERCENT_ESTIMATE));
        if(ingredient.has(PERCENT_MIN))
            this.setPercentMin(ingredient.getInt(PERCENT_MIN));
        if(ingredient.has(PERCENT_MAX))
            this.setPercentMax(ingredient.getInt(PERCENT_MAX));
        if(ingredient.has(PROCESSING))
            this.setProcessing(ingredient.getString(PROCESSING));
        if(ingredient.has(TEXT))
            this.setText(ingredient.getString(TEXT));
        if(ingredient.has(VEGAN))
            this.setVegan(ingredient.getString(VEGAN).equals("yes"));
        if(ingredient.has(VEGETARIAN))
            this.setVegetarian(ingredient.getString(VEGETARIAN).equals("yes"));
    }
}
