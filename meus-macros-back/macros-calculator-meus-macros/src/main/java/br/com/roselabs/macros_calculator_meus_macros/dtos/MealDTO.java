package br.com.roselabs.macros_calculator_meus_macros.dtos;

import br.com.roselabs.macros_calculator_meus_macros.entities.Meal;
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
    private Long id;
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

    public MealDTO(Meal meal) {
        this.setId(meal.getId());
        this.setMealName(meal.getMealName());
        this.setCalories(meal.getCalories());
        this.setProtein(meal.getProtein());
        this.setCarbohydrates(meal.getCarbohydrates());
        this.setFat(meal.getFat());

        // Converte os itens da refeição para MealItemDTO
        meal.getItems().forEach(item -> {
            MealItemDTO itemDTO = new MealItemDTO();
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setCalories(item.getCalories());
            itemDTO.setProtein(item.getProtein());
            itemDTO.setCarbohydrates(item.getCarbohydrates());
            itemDTO.setFat(item.getFat());
            this.getItems().add(itemDTO);
        });
    }

}
