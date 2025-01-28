package br.com.roselabs.ai_meus_macros.dtos;

import lombok.Data;

@Data
public class FoodItemDTO {
    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    private String unit;
    private Integer portions;
}