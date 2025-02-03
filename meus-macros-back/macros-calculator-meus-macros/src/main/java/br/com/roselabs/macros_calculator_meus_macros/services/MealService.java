package br.com.roselabs.macros_calculator_meus_macros.services;

import br.com.roselabs.macros_calculator_meus_macros.dtos.MealDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealFilter;
import br.com.roselabs.macros_calculator_meus_macros.dtos.MealItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.entities.Meal;
import br.com.roselabs.macros_calculator_meus_macros.entities.MealItem;
import br.com.roselabs.macros_calculator_meus_macros.repositories.MealItemRepository;
import br.com.roselabs.macros_calculator_meus_macros.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    /**
     * Lista as refeições de um usuário para uma data específica.
     *
     * @param mealFilter filtro com a data (do tipo java.util.Date)
     * @param userUuid   identificador do usuário
     * @return lista de MealDTO para as refeições encontradas
     */
    public List<MealDTO> listMeals(MealFilter mealFilter, UUID userUuid) {
        // Define o início e o fim do dia
        LocalDateTime startOfDay = mealFilter.getDate().atStartOfDay();
        LocalDateTime endOfDay = mealFilter.getDate().atTime(LocalTime.MAX);

        // Busca as refeições do usuário no intervalo do dia
        List<Meal> meals = mealRepository.findByUserUuidAndDateBetween(userUuid, startOfDay, endOfDay);

        // Converte cada Meal para MealDTO
        return meals.stream()
                .map(this::convertToMealDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte a entidade Meal para o DTO correspondente.
     *
     * @param meal a entidade Meal
     * @return o MealDTO convertido
     */
    private MealDTO convertToMealDTO(Meal meal) {
        MealDTO dto = new MealDTO();
        dto.setMealName(meal.getMealName());
        dto.setCalories(meal.getCalories());
        dto.setProtein(meal.getProtein());
        dto.setCarbohydrates(meal.getCarbohydrates());
        dto.setFat(meal.getFat());

        // Converte os itens da refeição para MealItemDTO
        meal.getItems().forEach(item -> {
            MealItemDTO itemDTO = new MealItemDTO();
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setCalories(item.getCalories());
            itemDTO.setProtein(item.getProtein());
            itemDTO.setCarbohydrates(item.getCarbohydrates());
            itemDTO.setFat(item.getFat());
            dto.getItems().add(itemDTO);
        });

        return dto;
    }
}
