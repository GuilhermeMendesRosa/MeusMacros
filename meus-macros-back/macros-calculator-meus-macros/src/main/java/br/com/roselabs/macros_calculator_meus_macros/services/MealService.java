package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.dtos.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.Meal;
import br.com.roselabs.macros_calculator_meus_macros.entities.MealItem;
import br.com.roselabs.macros_calculator_meus_macros.repositories.MealItemRepository;
import br.com.roselabs.macros_calculator_meus_macros.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {

    private final MealRepository mealRepository;
    private final MealItemRepository mealItemRepository;
    private final JWTService jwtService;

    public Meal createMeal(MealDTO mealDTO, UUID userUuid) {
        Meal meal = new Meal();
        meal.setUserUuid(userUuid);
        meal.setDate(LocalDateTime.now());

        List<MealItem> items = mealDTO.getItems().stream()
                .map(this::convertToEntity)
                .peek(item -> item.setMeal(meal))
                .toList();

        meal.setMealName(mealDTO.getMealName());
        meal.setCalories(items.stream().mapToInt(MealItem::getCalories).sum());
        meal.setProtein(items.stream().mapToInt(MealItem::getProtein).sum());
        meal.setCarbohydrates(items.stream().mapToInt(MealItem::getCarbohydrates).sum());
        meal.setFat(items.stream().mapToInt(MealItem::getFat).sum());

        meal.setItems(items);
        return mealRepository.save(meal);
    }

    private MealItem convertToEntity(MealItemDTO dto) {
        MealItem item = new MealItem();
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        item.setCalories(dto.getCalories());
        item.setProtein(dto.getProtein());
        item.setCarbohydrates(dto.getCarbohydrates());
        item.setFat(dto.getFat());
        return item;
    }

}