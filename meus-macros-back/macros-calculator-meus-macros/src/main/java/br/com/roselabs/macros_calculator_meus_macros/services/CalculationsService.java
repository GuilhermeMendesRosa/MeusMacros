package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.clients.AIClient;
import br.com.roselabs.macros_calculator_meus_macros.clients.FoodBaseClient;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.Meal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculationsService {

    private final AIClient aiClient;
    private final FoodBaseClient foodBaseClient;

    public Meal calculate(String transcriptFood) {
        List<FoodDTO> foodDTOS = this.aiClient.convertTranscriptToList(transcriptFood);
        List<FoodItemDTO> foodItems = this.foodBaseClient.findFoodItems(foodDTOS);
        Meal meal = new Meal(foodItems);

        return meal;
    }

}
