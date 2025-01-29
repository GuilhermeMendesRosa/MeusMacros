package br.com.roselabs.macros_calculator_meus_macros.repositories;

import br.com.roselabs.macros_calculator_meus_macros.entities.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealItemRepository extends JpaRepository<MealItem, Long> {
}