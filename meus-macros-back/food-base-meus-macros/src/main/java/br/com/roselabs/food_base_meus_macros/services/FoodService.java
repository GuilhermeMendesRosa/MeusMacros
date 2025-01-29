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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    private final FoodItemRepository foodItemRepository;
    private final AIClient aiClient;

    public void generateEmbeddings() {
        logger.info("Iniciando geração de embeddings para itens de comida sem embeddings.");

        List<FoodItem> foodsWithoutEmbedding = foodItemRepository.findFoodsWithoutEmbedding();
        logger.debug("{} itens de comida encontrados sem embeddings.", foodsWithoutEmbedding.size());

        for (FoodItem foodItem : foodsWithoutEmbedding) {
            try {
                logger.debug("Gerando embedding para: {}", foodItem.getName());
                foodItem.setEmbedding(getEmbedding(foodItem.getName()));
                foodItemRepository.save(foodItem);
                logger.info("Embedding salvo com sucesso para: {}", foodItem.getName());
            } catch (Exception e) {
                logger.error("Erro ao gerar ou salvar embedding para: {}", foodItem.getName(), e);
            }

        }

        logger.info("Geração de embeddings concluída.");
    }

    private List<Double> getEmbedding(String foodItemName) {
        logger.debug("Chamando cliente de IA para gerar embedding de: {}", foodItemName);
        return aiClient.generateEmbedding(foodItemName);
    }

    public List<FoodItemDTO> findFoodItems(List<FoodDTO> foodDTOs) {
        logger.info("Iniciando busca de itens de comida com base nos embeddings fornecidos.");

        List<FoodItemDTO> foodItemDTOS = foodDTOs.stream().map(this::findNearestFoodItem).collect(Collectors.toList());

        logger.info("Busca concluída. {} correspondências encontradas.", foodItemDTOS.size());
        return foodItemDTOS;
    }

    private FoodItemDTO findNearestFoodItem(FoodDTO foodDTO) {
        logger.debug("Buscando vizinho mais próximo para: {}", foodDTO.getName());

        List<FoodItem> foodItems = foodItemRepository.findNearestNeighbors(foodDTO.getEmbedding().toString());
        List<FoodItemChooseDTO> itemsToChoose = foodItems.stream()
                .map(FoodItemChooseDTO::new)
                .collect(Collectors.toList());

        String chosenId = aiClient.chooseId(new ChooseDTO(foodDTO.getName(), itemsToChoose));

        return foodItems.stream()
                .filter(foodItem -> foodItem.getId().toString().equals(chosenId))
                .findFirst()
                .map(foodItem -> {
                    logger.info("Vizinho mais próximo encontrado para {} -> {}", foodDTO.getName(), foodItem.getName());
                    return new FoodItemDTO(foodItem, foodDTO.getPortions());
                })
                .orElseGet(() -> {
                    logger.warn("Nenhum vizinho mais próximo encontrado para: {}", foodDTO.getName());
                    return null;
                });
    }
}