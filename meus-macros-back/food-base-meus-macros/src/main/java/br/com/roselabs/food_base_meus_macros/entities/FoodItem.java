package br.com.roselabs.food_base_meus_macros.entities;

import br.com.roselabs.food_base_meus_macros.enums.Unit;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "food_item")
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

    @Basic
    @Type(JsonType.class)
    @Column(name = "embedding", columnDefinition = "vector")
    private List<Double> embedding;

}