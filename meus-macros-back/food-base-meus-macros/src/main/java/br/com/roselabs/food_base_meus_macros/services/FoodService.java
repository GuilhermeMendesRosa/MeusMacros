package br.com.roselabs.food_base_meus_macros.services;

import br.com.roselabs.food_base_meus_macros.clients.AIClient;
import br.com.roselabs.food_base_meus_macros.dtos.ChooseDTO;
import br.com.roselabs.food_base_meus_macros.dtos.FoodDTO;
import br.com.roselabs.food_base_meus_macros.dtos.FoodItemChooseDTO;
import br.com.roselabs.food_base_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import br.com.roselabs.food_base_meus_macros.repositories.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    //TODO refatorar classe

    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    private final FoodItemRepository foodItemRepository;
    private final AIClient aiClient;

    public void generateEmbeddings() {
        logger.info("Starting embedding generation for food items without embeddings.");
        List<FoodItem> foodsWithoutEmbedding = this.foodItemRepository.findFoodsWithoutEmbedding();
        logger.debug("Found {} food items without embeddings.", foodsWithoutEmbedding.size());

        for (FoodItem foodItem : foodsWithoutEmbedding) {
            try {
                logger.debug("Generating embedding for food item: {}", foodItem.getName());
                List<Double> embedding = getEmbedding(foodItem.getName());
                foodItem.setEmbedding(embedding);
                this.foodItemRepository.save(foodItem);
                logger.info("Successfully saved embedding for food item: {}", foodItem.getName());
            } catch (Exception e) {
                logger.error("Error generating or saving embedding for food item: {}", foodItem.getName(), e);
            }
        }
        logger.info("Finished embedding generation.");
    }

    private List<Double> getEmbedding(String foodItem) {
        logger.debug("Calling AI client to generate embedding for: {}", foodItem);
        List<Double> embedding = this.aiClient.generateEmbedding(foodItem);
        logger.debug("Received embedding for food item: {}", foodItem);
        return embedding;
    }

    public List<FoodItemDTO> findFoodItems(List<FoodDTO> foodDTOs) {
        logger.info("Starting search for food items based on provided embeddings.");
        List<FoodItemDTO> foodItemDTOS = new ArrayList<>();

        for (FoodDTO foodDTO : foodDTOs) {
            logger.debug("Searching for nearest neighbor for food: {}", foodDTO.getName());
            String embedding = foodDTO.getEmbedding().toString();
            List<FoodItem> foodItems = this.foodItemRepository.findNearestNeighbors(embedding);

            List<FoodItemChooseDTO> itemsToChoose = new ArrayList<>();
            for (FoodItem foodItem : foodItems) {
                FoodItemChooseDTO itemChooseDTO = new FoodItemChooseDTO(foodItem);
                itemsToChoose.add(itemChooseDTO);
            }

            ChooseDTO chooseDTO = new ChooseDTO(foodDTO.getName(), itemsToChoose);

            String chosenId = this.aiClient.chooseId(chooseDTO);

            FoodItem foodItem = null;

            for (FoodItem foodItem1 : foodItems) {
                if (chosenId.equals(foodItem1.getId().toString())) {
                    foodItem = foodItem1;
                }
            }

            if (foodItem != null) {
                foodItemDTOS.add(new FoodItemDTO(foodItem, foodDTO.getPortions()));
                logger.info("Found nearest neighbor for food: {} -> {}", foodDTO.getName(), foodItem.getName());
            } else {
                logger.warn("No nearest neighbor found for food: {}", foodDTO.getName());
            }
        }

        logger.info("Finished search for food items. Found {} matches.", foodItemDTOS.size());
        return foodItemDTOS;
    }
}