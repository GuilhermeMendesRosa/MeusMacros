package br.com.roselabs.auth_meus_macros.data;

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

}
