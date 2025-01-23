package br.com.roselabs.macros_calculator_meus_macros.clients;


import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "food-base-meus-macros")
public interface FoodBaseClient {

    @RequestMapping(method = RequestMethod.POST, value = "/find-food-items")
    List<FoodItemDTO> findFoodItems(@RequestBody List<FoodDTO> foodDTOs);

}
