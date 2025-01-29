package br.com.roselabs.macros_calculator_meus_macros.repositories;

import br.com.roselabs.macros_calculator_meus_macros.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}
