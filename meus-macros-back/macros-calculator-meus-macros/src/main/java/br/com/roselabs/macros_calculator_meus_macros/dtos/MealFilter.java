package br.com.roselabs.macros_calculator_meus_macros.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MealFilter {
    private LocalDate date;
}
