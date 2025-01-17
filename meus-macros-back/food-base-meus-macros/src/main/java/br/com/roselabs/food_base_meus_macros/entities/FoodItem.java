package br.com.roselabs.food_base_meus_macros.entities;

import br.com.roselabs.food_base_meus_macros.enums.Unit;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;

    @Enumerated(EnumType.STRING)
    private Unit unit;
}
