package br.com.roselabs.macros_calculator_meus_macros.entities;

import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Meal {

    private List<String> foods = new ArrayList<>();
    private Double calories = 0.0;
    private Double protein = 0.0;
    private Double carbohydrates = 0.0;
    private Double fat = 0.0;

    public Meal(List<FoodItemDTO> foodItems) {
        for (FoodItemDTO foodItem : foodItems) {
            this.foods.add(String.format("%d%s de %s", foodItem.getPortions(), foodItem.getUnit(), foodItem.getFoodItemName()));
            this.calories += foodItem.getCalories() * foodItem.getPortions();
            this.protein += foodItem.getProtein() * foodItem.getPortions();
            this.carbohydrates += foodItem.getCarbohydrates() * foodItem.getPortions();
            this.fat += foodItem.getFat() * foodItem.getPortions();
        }
    }
}
