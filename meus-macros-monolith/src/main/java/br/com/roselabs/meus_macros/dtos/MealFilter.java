package br.com.roselabs.meus_macros.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MealFilter {
    private LocalDate date;
}
