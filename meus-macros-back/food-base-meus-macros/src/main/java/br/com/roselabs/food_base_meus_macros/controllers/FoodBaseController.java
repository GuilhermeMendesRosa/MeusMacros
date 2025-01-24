package br.com.roselabs.food_base_meus_macros.controllers;

import br.com.roselabs.food_base_meus_macros.dtos.FoodDTO;
import br.com.roselabs.food_base_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.food_base_meus_macros.services.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodBaseController {

    private final FoodService foodService;

    @GetMapping("/generate-embeddings")
    @Transactional
    public ResponseEntity<String> generateEmbeddings() throws InterruptedException {
        foodService.generateEmbeddings();
        return ResponseEntity.ok("Embeddings generated successfully");
    }

    @PostMapping("/find-food-items")
    public ResponseEntity<List<FoodItemDTO>> findFoodItems(@RequestBody List<FoodDTO> foodDTOs) {
        return ResponseEntity.ok(this.foodService.findFoodItems(foodDTOs));
    }

}
