package br.com.roselabs.macros_calculator_meus_macros.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {

    private String name;
    private String unit;
    private Integer portions;
    private Boolean inNatura;
    private List<Double> embedding;

}
