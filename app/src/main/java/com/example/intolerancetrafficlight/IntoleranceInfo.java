package com.example.intolerancetrafficlight;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IntoleranceInfo {
    Map<IntoleranceEnum, Intolerance> Intolerances;
    Map<IntoleranceEnum,Map<Locale,String>> localizedNames;

    Map<IntoleranceEnum,String[]> supportecLocales;


    public Map<IntoleranceEnum, Map<Locale, String>> getLocalizedNames() {
        return localizedNames;
    }

    public void setLocalizedNames(Map<IntoleranceEnum, Map<Locale, String>> localizedNames) {
        this.localizedNames = localizedNames;
    }

    public Map<IntoleranceEnum, String[]> getSupportecLocales() {
        return supportecLocales;
    }

    public void setSupportecLocales(Map<IntoleranceEnum, String[]> supportecLocales) {
        this.supportecLocales = supportecLocales;
    }

    public IntoleranceInfo(Map<IntoleranceEnum, Intolerance> intolerances, Map<IntoleranceEnum,Map<Locale,String>> localizedNames , Map<IntoleranceEnum,String[]>supportecLocales) {
        this.Intolerances = intolerances;
        this.localizedNames = localizedNames;
        this.supportecLocales = supportecLocales;
    }

    public Map<IntoleranceEnum, Intolerance> getIntolerances() {
        return Intolerances;
    }

    public void setIntolerances(Map<IntoleranceEnum, Intolerance> intolerances) {
        Intolerances = intolerances;
    }
}
