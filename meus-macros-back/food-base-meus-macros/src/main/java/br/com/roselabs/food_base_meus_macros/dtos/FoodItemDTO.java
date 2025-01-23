package br.com.roselabs.food_base_meus_macros.dtos;

import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import br.com.roselabs.food_base_meus_macros.enums.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodItemDTO {
    private Long id;
    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    private Unit unit;
    private String foodItemName;
    private Integer portions;

    public FoodItemDTO(FoodItem foodItem, String foodItemName, Integer portions) {
        this.id = foodItem.getId();
        this.name = foodItem.getName();
        this.calories = foodItem.getCalories();
        this.protein = foodItem.getProtein();
        this.carbohydrates = foodItem.getCarbohydrates();
        this.fat = foodItem.getFat();
        this.unit = foodItem.getUnit();
        this.foodItemName = foodItemName;
        this.portions = portions;
    }

}
