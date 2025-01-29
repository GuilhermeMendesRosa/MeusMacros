package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.clients.AIClient;
import br.com.roselabs.macros_calculator_meus_macros.clients.FoodBaseClient;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.TranscriptDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.MealDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationsService {

    private final AIClient aiClient;
    private final FoodBaseClient foodBaseClient;

    public MealDTO calculate(TranscriptDTO transcriptFood) {
        log.info("Iniciando cálculo das refeições com base na transcrição recebida.");

        List<FoodDTO> foodDTOS = aiClient.convertTranscriptToList(transcriptFood);
        log.info("{} itens alimentares identificados na transcrição.", foodDTOS.size());

        List<FoodDTO> genericFoods = foodDTOS.stream()
                .filter(FoodDTO::getIsGenericFood)
                .collect(Collectors.toList());
        List<FoodDTO> nonGenericFoods = foodDTOS.stream()
                .filter(foodDTO -> !foodDTO.getIsGenericFood())
                .collect(Collectors.toList());

        log.info("Itens genéricos (in natura): {}", genericFoods.size());
        log.info("Itens processados: {}", nonGenericFoods.size());

        List<FoodItemDTO> foodBaseClientFoodItems = genericFoods.isEmpty() ?
                new ArrayList<>() : foodBaseClient.findFoodItems(genericFoods);

        List<FoodItemDTO> aiClientFoodItems = nonGenericFoods.isEmpty() ?
                new ArrayList<>() : aiClient.findFoodItems(nonGenericFoods);

        List<FoodItemDTO> combinedFoodItems = new ArrayList<>();
        combinedFoodItems.addAll(foodBaseClientFoodItems);
        combinedFoodItems.addAll(aiClientFoodItems);

        log.info("Total de itens combinados para a refeição: {}", combinedFoodItems.size());

        return new MealDTO(combinedFoodItems);
    }
}