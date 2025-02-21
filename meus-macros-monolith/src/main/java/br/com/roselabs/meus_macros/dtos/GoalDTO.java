package br.com.roselabs.meus_macros.dtos;

import br.com.roselabs.meus_macros.entities.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {

    private Long id;
    private LocalDateTime date;
    private Integer calories;
    private Integer proteinPercentage;
    private Integer carbohydratesPercentage;
    private Integer fatPercentage;

    // Construtor que recebe uma entidade Goal
    public GoalDTO(Goal goal) {
        this.id = goal.getId();
        this.date = goal.getDate();
        this.calories = goal.getCalories();
        this.proteinPercentage = goal.getProteinPercentage();
        this.carbohydratesPercentage = goal.getCarbohydratesPercentage();
        this.fatPercentage = goal.getFatPercentage();
    }
}
