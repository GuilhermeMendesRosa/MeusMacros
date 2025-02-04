package br.com.roselabs.macros_calculator_meus_macros.controllers;

import br.com.roselabs.macros_calculator_meus_macros.dtos.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealFilter;
import br.com.roselabs.macros_calculator_meus_macros.services.JWTService;
import br.com.roselabs.macros_calculator_meus_macros.services.MealService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meals")
@AllArgsConstructor
public class MealsController {

    private final MealService mealService;
    private final JWTService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createMeal(@RequestBody MealDTO mealDTO, @RequestHeader("Authorization") String token) {
        UUID userUuid = jwtService.getUUIDFromToken(token.replace("Bearer ", ""));
        this.mealService.createMeal(mealDTO, userUuid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<MealDTO>> listMeals(@RequestBody MealFilter mealFilter, @RequestHeader("Authorization") String token) {
        UUID userUuid = jwtService.getUUIDFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(this.mealService.listMeals(mealFilter, userUuid));
    }

    // Rota DELETE para excluir uma refeição
    @DeleteMapping("/delete/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId, @RequestHeader("Authorization") String token) {
        UUID userUuid = jwtService.getUUIDFromToken(token.replace("Bearer ", ""));
        mealService.deleteMeal(mealId, userUuid);
        return ResponseEntity.noContent().build();
    }
}
