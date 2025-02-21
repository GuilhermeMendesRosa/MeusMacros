package br.com.roselabs.meus_macros.repositories;

import br.com.roselabs.meus_macros.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findByUserUuidAndDateBetween(UUID userUuid, LocalDateTime start, LocalDateTime end);

    Optional<Meal> findById(Long mealId);
}
