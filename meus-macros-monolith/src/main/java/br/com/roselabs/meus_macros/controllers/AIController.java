package br.com.roselabs.meus_macros.controllers;

import br.com.roselabs.meus_macros.data.Transcript;
import br.com.roselabs.meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.meus_macros.services.AIService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/ai")
@AllArgsConstructor
public class AIController {

    private final AIService service;

    @PostMapping("/generate")
    public ResponseEntity<List<FoodItemDTO>> generate(@RequestBody Transcript transcript) {
        List<FoodItemDTO> foods = this.service.generate(transcript);
        return ResponseEntity.ok(foods);
    }

}
