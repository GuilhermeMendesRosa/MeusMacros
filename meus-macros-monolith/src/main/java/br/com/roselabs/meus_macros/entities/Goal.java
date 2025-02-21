package br.com.roselabs.meus_macros.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goals")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "calories", nullable = false)
    private Integer calories;

    @Column(name = "protein_percentage", nullable = false)
    private Integer proteinPercentage;

    @Column(name = "carbohydrates_percentage", nullable = false)
    private Integer carbohydratesPercentage;

    @Column(name = "fat_percentage", nullable = false)
    private Integer fatPercentage;

}
