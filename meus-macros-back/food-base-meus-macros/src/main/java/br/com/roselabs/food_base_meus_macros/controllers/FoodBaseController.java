package br.com.roselabs.food_base_meus_macros.controllers;

import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import br.com.roselabs.food_base_meus_macros.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodBaseController {

    private final FoodService foodService;

    @PostMapping("/generate-embeddings")
    public ResponseEntity<String> generateEmbeddings() {
        foodService.generateEmbeddings();
        return ResponseEntity.ok("Embeddings generated successfully");
    }

    @PostMapping("/find-nearest-neighbors")
    public ResponseEntity<List<FoodItem>> findNearestNeighbors() {
        List<Double> carneDePatinhio = foodService.getEmbedding("Carne de patinhio");
        List<FoodItem> nearestNeighbors = foodService.findNearestNeighbors(carneDePatinhio.toString());
        return ResponseEntity.ok(nearestNeighbors);
    }

}
