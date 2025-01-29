package br.com.roselabs.ai_meus_macros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemChooseDTO {

    private Long id;
    private String name;

}