package br.com.roselabs.food_base_meus_macros.services;

import br.com.roselabs.food_base_meus_macros.clients.AIClient;
import br.com.roselabs.food_base_meus_macros.dtos.FoodDTO;
import br.com.roselabs.food_base_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import br.com.roselabs.food_base_meus_macros.repositories.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final AIClient aiClient;

    public void generateEmbeddings() {
        List<FoodItem> byNameContainingCarne = this.foodItemRepository.findFoodsWithoutEmbedding();
        byNameContainingCarne
                .forEach(foodItem -> {
                    List<Double> embedding = getEmbedding(foodItem.getName());
                    foodItem.setEmbedding(embedding);
                    this.foodItemRepository.save(foodItem);
                });
    }

    private List<Double> getEmbedding(String foodItem) {
        return this.aiClient.generateEmbedding(foodItem);
    }

    public List<FoodItemDTO> findFoodItems(List<FoodDTO> foodDTOs) {
        List<FoodItemDTO> foodItemDTOS = new ArrayList<>();

        for (FoodDTO foodDTO : foodDTOs) {
            Optional<FoodItem> foodItemOptional = this.foodItemRepository.findNearestNeighbors(
                    foodDTO.getEmbedding().toString());
            foodItemOptional.ifPresent(foodItem ->
                    foodItemDTOS.add(new FoodItemDTO(foodItem, foodDTO.getName(), foodDTO.getPortions())));
        }

        return foodItemDTOS;
    }
}
