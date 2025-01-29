package br.com.roselabs.food_base_meus_macros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseDTO {

    private String userInput;
    private List<FoodItemChooseDTO> itemsToChoose;

}
