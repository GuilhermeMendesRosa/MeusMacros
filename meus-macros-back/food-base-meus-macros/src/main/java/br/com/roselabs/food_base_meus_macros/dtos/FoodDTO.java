package br.com.roselabs.food_base_meus_macros.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {

    private String name;
    private String unit;
    private Integer portions;
    private List<Double> embedding;

}
