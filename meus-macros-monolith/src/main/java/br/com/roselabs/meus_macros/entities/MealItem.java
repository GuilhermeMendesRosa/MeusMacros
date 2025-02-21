package br.com.roselabs.meus_macros.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meal_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    private String name;
    private String quantity;
    private int calories;
    private int protein;
    private int carbohydrates;
    private int fat;
}