package br.com.roselabs.macros_calculator_meus_macros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealItemDTO {

    private String name;
    private int calories = 0;
    private int protein = 0;
    private int carbohydrates = 0;
    private int fat = 0;
    private String quantity;

    public MealItemDTO(FoodItemDTO foodItemDTO) {
        this.name = foodItemDTO.getName();
        this.calories += (int) (foodItemDTO.getCalories() * foodItemDTO.getPortions());
        this.protein += (int) (foodItemDTO.getProtein() * foodItemDTO.getPortions());
        this.carbohydrates += (int) (foodItemDTO.getCarbohydrates() * foodItemDTO.getPortions());
        this.fat += (int) (foodItemDTO.getFat() * foodItemDTO.getPortions());
        this.quantity = String.format("%d%s", foodItemDTO.getPortions(), foodItemDTO.getUnit());
    }

}
