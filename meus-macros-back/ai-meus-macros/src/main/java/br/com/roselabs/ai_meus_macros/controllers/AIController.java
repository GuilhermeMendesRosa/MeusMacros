package br.com.roselabs.ai_meus_macros.controllers;

import br.com.roselabs.ai_meus_macros.data.Food;
import br.com.roselabs.ai_meus_macros.services.AIService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@AllArgsConstructor
public class AIController {

    private final AIService service;

    @PostMapping("/transcript-to-list")
    public ResponseEntity<List<Food>> convertTranscriptToList(@RequestBody String transcript) throws InterruptedException {
        List<Food> foods = this.service.convertTranscriptToList(transcript);
        return ResponseEntity.ok(foods);
    }

    @PostMapping("/generate-embedding")
    public ResponseEntity<List<Double>> generateEmbedding(@RequestBody String foodName) {
        List<Double> embedding = this.service.generateEmbedding(foodName);
        return ResponseEntity.ok(embedding);
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

}
