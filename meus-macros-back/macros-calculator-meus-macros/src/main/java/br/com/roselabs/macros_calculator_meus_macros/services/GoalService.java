package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.dtos.GoalDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.Goal;
import br.com.roselabs.macros_calculator_meus_macros.repositories.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public GoalDTO getLatestGoal(UUID userUuid) {
        Goal goal = goalRepository.findLatestGoalByUser(userUuid)
                .orElseThrow(() -> new RuntimeException("Nenhum objetivo encontrado para o usuário."));
        return new GoalDTO(goal);
    }

    public GoalDTO createGoal(UUID userUuid, GoalDTO goalDTO) {
        Goal goal = new Goal();
        goal.setUserUuid(userUuid);
        goal.setDate(LocalDateTime.now());
        goal.setCalories(goalDTO.getCalories());
        goal.setProteinPercentage(goalDTO.getProteinPercentage());
        goal.setCarbohydratesPercentage(goalDTO.getCarbohydratesPercentage());
        goal.setFatPercentage(goalDTO.getFatPercentage());

        Goal savedGoal = goalRepository.save(goal);

        return new GoalDTO(savedGoal);
    }

}
