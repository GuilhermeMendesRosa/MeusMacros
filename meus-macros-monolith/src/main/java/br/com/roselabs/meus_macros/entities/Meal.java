package br.com.roselabs.meus_macros.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meals")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(nullable = false)
    private LocalDateTime date;

    private int calories;
    private int protein;
    private int carbohydrates;
    private int fat;

    // Adicionando o novo campo mealName
    @Column(name = "meal_name")
    private String mealName;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealItem> items = new ArrayList<>();
}
