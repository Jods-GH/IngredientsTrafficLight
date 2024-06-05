package com.example.intolerancetrafficlight;

import java.util.List;
import java.util.Map;

public class IntoleranceInfo {
    Map<Intolerances, List<Intolerance>> Intolerances;


    public IntoleranceInfo(Map<com.example.intolerancetrafficlight.Intolerances, List<Intolerance>> intolerances) {
        Intolerances = intolerances;
    }

    public Map<com.example.intolerancetrafficlight.Intolerances, List<Intolerance>> getIntolerances() {
        return Intolerances;
    }

    public void setIntolerances(Map<com.example.intolerancetrafficlight.Intolerances, List<Intolerance>> intolerances) {
        Intolerances = intolerances;
    }
}
