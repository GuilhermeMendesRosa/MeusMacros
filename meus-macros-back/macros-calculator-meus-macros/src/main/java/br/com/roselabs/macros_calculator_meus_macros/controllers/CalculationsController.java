package br.com.roselabs.macros_calculator_meus_macros.controllers;

import br.com.roselabs.macros_calculator_meus_macros.entities.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.services.CalculationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CalculationsController {

    private final CalculationsService calculationsService;

    @PostMapping("/calculate")
    public ResponseEntity<MealDTO> calculateMacros(@RequestBody String transcriptFood) {
        MealDTO mealDTO = this.calculationsService.calculate(transcriptFood);
        return ResponseEntity.ok(mealDTO);
    }

}
