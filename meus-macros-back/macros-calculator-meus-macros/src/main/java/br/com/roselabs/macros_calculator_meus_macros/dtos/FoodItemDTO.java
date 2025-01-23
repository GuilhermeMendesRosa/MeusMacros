package br.com.roselabs.macros_calculator_meus_macros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodItemDTO {
    private Long id;
    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    private String unit;
    private String foodItemName;
    private Integer portions;

}