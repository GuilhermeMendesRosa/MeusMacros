package br.com.roselabs.food_base_meus_macros.enums;

public enum Unit {
    GRAM("g"),
    MILLILITER("ml");

    private final String abbreviation;

    Unit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
