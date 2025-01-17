package br.com.roselabs.food_base_meus_macros.enums;

import lombok.Getter;

@Getter
public enum Unit {
    GRAM("g"),
    MILLILITER("ml");

    private final String abbreviation;

    Unit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
