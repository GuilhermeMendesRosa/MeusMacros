package br.com.roselabs.food_base_meus_macros.dtos;

import br.com.roselabs.food_base_meus_macros.entities.FoodItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemChooseDTO {

    private Long id;
    private String name;

    public FoodItemChooseDTO(FoodItem foodItem) {
        this.id = foodItem.getId();
        this.name = foodItem.getName();
    }

}
