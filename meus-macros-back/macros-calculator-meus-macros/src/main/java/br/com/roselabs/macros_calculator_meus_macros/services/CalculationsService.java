package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.clients.AIClient;
import br.com.roselabs.macros_calculator_meus_macros.clients.FoodBaseClient;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.MealDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculationsService {

    //TODO refatorar classe

    private final AIClient aiClient;
    private final FoodBaseClient foodBaseClient;

    public MealDTO calculate(String transcriptFood) {
        List<FoodDTO> foodDTOS = this.aiClient.convertTranscriptToList(transcriptFood);

        List<FoodDTO> foodItemDTOsInNatura = new ArrayList<>();
        List<FoodDTO> foodItemDTOsNotInNatura = new ArrayList<>();

        for (FoodDTO foodDTO : foodDTOS) {
            if (foodDTO.getIsGenericFood()) {
                foodItemDTOsInNatura.add(foodDTO);
            } else {
                foodItemDTOsNotInNatura.add(foodDTO);
            }
        }


        List<FoodItemDTO> foodBaseClientFoodItems = new ArrayList<>();
        List<FoodItemDTO> aiClientFoodItems = new ArrayList<>();

        if (!foodItemDTOsInNatura.isEmpty()) {
            foodBaseClientFoodItems = this.foodBaseClient.findFoodItems(foodItemDTOsInNatura);
        }

        if (!foodItemDTOsNotInNatura.isEmpty()) {
            aiClientFoodItems = this.aiClient.findFoodItems(foodItemDTOsNotInNatura);
        }

        List<FoodItemDTO> combinedFoodItems = new ArrayList<>();

        combinedFoodItems.addAll(foodBaseClientFoodItems);
        combinedFoodItems.addAll(aiClientFoodItems);

        MealDTO mealDTO = new MealDTO(combinedFoodItems);

        return mealDTO;
    }

}
