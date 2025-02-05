package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.clients.AIClient;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.TranscriptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationsService {

    private final AIClient aiClient;

    public MealDTO calculate(TranscriptDTO transcriptFood) {
        log.info("Iniciando cálculo das refeições com base na transcrição recebida.");

        List<FoodItemDTO> foodDTOS = aiClient.generate(transcriptFood);

        return new MealDTO(foodDTOS);
    }
}