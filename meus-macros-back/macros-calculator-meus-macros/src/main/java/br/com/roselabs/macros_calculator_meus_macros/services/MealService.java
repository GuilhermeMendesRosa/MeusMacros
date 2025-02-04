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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
                .map(MealDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Exclui uma refeição, verificando se ela pertence ao usuário.
     *
     * @param mealId   identificador da refeição a ser excluída
     * @param userUuid identificador do usuário (obtido via token JWT)
     */
    public void deleteMeal(Long mealId, UUID userUuid) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Refeição não encontrada"));

        if (!meal.getUserUuid().equals(userUuid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado para excluir esta refeição");
        }

        mealRepository.delete(meal);
    }
}
