package br.com.roselabs.macros_calculator_meus_macros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private final List<MealItemDTO> items = new ArrayList<>();
    private String mealName;
    private int calories = 0;
    private int protein = 0;
    private int carbohydrates = 0;
    private int fat = 0;

    public MealDTO(List<FoodItemDTO> foodItemDTOS) {
        for (FoodItemDTO foodItemDTO : foodItemDTOS) {
            MealItemDTO mealItemDTO = new MealItemDTO(foodItemDTO);

            this.items.add(mealItemDTO);
            this.calories += mealItemDTO.getCalories();
            this.protein += mealItemDTO.getProtein();
            this.carbohydrates += mealItemDTO.getCarbohydrates();
            this.fat += mealItemDTO.getFat();
        }
    }
}
