package br.com.roselabs.food_base_meus_macros.repositories;

import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM food_item ORDER BY embedding <-> cast(? as vector) LIMIT 1")
    Optional<FoodItem> findNearestNeighbors(String embedding);

    @Query(nativeQuery = true,
            value = "SELECT * FROM food_item WHERE embedding IS NULL")
    List<FoodItem> findFoodsWithoutEmbedding();

}