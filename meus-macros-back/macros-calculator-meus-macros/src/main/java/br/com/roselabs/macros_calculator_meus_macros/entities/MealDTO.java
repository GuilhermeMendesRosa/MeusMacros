package br.com.roselabs.macros_calculator_meus_macros.entities;

import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MealDTO {

    private final List<String> foods = new ArrayList<>();
    private int calories = 0;
    private int protein = 0;
    private int carbohydrates = 0;
    private int fat = 0;

    public MealDTO(List<FoodItemDTO> foodItems) {
        for (FoodItemDTO foodItem : foodItems) {
            this.foods.add(String.format("%d%s de %s", foodItem.getPortions(), foodItem.getUnit(), foodItem.getName()));
            this.calories += (int) (foodItem.getCalories() * foodItem.getPortions());
            this.protein += (int) (foodItem.getProtein() * foodItem.getPortions());
            this.carbohydrates += (int) (foodItem.getCarbohydrates() * foodItem.getPortions());
            this.fat += (int) (foodItem.getFat() * foodItem.getPortions());
        }
    }
}
