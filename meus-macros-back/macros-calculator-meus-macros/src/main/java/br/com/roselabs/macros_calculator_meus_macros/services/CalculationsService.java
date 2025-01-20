package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.clients.AIClient;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculationsService {

    private final AIClient aiClient;

    public List<FoodDTO> calculate(String transcriptFood) {
        List<FoodDTO> foodDTOS = this.aiClient.convertTranscriptToList(transcriptFood);
        return foodDTOS;
    }

}
