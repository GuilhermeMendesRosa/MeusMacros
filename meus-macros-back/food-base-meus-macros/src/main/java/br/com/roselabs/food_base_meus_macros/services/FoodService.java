package br.com.roselabs.food_base_meus_macros.services;

import br.com.roselabs.food_base_meus_macros.clients.AIClient;
import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import br.com.roselabs.food_base_meus_macros.repositories.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final AIClient aiClient;

    public void generateEmbeddings() {
        List<FoodItem> byNameContainingCarne = foodItemRepository.findFoodsWithouEmbedding();
        byNameContainingCarne
                .forEach(foodItem -> {
                    List<Double> embedding = getEmbedding(foodItem.getName());
                    foodItem.setEmbedding(embedding);
                    foodItemRepository.save(foodItem);
                });
    }

    public List<Double> getEmbedding(String foodItem) {
        return aiClient.generateEmbedding(foodItem);
    }

    public List<FoodItem> findNearestNeighbors(String embedding) {
        return foodItemRepository.findNearestNeighbors(embedding);
    }

}
