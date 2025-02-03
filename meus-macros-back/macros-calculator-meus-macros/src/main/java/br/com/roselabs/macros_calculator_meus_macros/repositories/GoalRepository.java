package br.com.roselabs.macros_calculator_meus_macros.repositories;

import br.com.roselabs.macros_calculator_meus_macros.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT g FROM Goal g WHERE g.userUuid = :userUuid ORDER BY g.date DESC LIMIT 1")
    Optional<Goal> findLatestGoalByUser(UUID userUuid);
}