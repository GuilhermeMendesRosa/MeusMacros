package br.com.roselabs.macros_calculator_meus_macros.controllers;

import br.com.roselabs.macros_calculator_meus_macros.dtos.TranscriptDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.services.CalculationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController()
@RequestMapping("/calculate")
@AllArgsConstructor
public class CalculationsController {

    private final CalculationsService calculationsService;

    @PostMapping()
    public ResponseEntity<MealDTO> calculateMacros(@RequestBody TranscriptDTO transcriptDTO) {
        MealDTO mealDTO = this.calculationsService.calculate(transcriptDTO);
        return ResponseEntity.ok(mealDTO);
    }

}
