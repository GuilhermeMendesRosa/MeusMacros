package br.com.roselabs.meus_macros.controllers;

import br.com.roselabs.meus_macros.dtos.GoalDTO;
import br.com.roselabs.meus_macros.services.GoalService;
import br.com.roselabs.meus_macros.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/goals")
@AllArgsConstructor
public class GoalsController {

    private final JWTService jwtService;
    private final GoalService goalService;

    @GetMapping()
    public ResponseEntity<GoalDTO> getLatestGoal(@RequestHeader("Authorization") String token) {
        UUID userUuid = jwtService.getSubjectFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(this.goalService.getLatestGoal(userUuid));
    }

    @PostMapping()
    public ResponseEntity<GoalDTO> createGoal(@RequestHeader("Authorization") String token, @RequestBody GoalDTO goalDTO) {
        UUID userUuid = jwtService.getSubjectFromToken((token.replace("Bearer ", "")));
        GoalDTO createdGoal = this.goalService.createGoal(userUuid, goalDTO);
        return ResponseEntity.ok(createdGoal);
    }
}
