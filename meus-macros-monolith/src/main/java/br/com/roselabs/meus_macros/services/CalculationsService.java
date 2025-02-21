package br.com.roselabs.meus_macros.services;

import br.com.roselabs.meus_macros.data.Transcript;
import br.com.roselabs.meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.meus_macros.dtos.MealDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationsService {

    private final AIService service;

    public MealDTO calculate(Transcript transcriptFood) {
        log.info("Iniciando cálculo das refeições com base na transcrição recebida.");

        List<FoodItemDTO> foodDTOS = service.generate(transcriptFood);

        return new MealDTO(foodDTOS);
    }
}