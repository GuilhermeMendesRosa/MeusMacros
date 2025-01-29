package br.com.roselabs.macros_calculator_meus_macros.clients;

import br.com.roselabs.macros_calculator_meus_macros.configs.SecurityConfig;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ai-meus-macros", configuration = SecurityConfig.class)
public interface AIClient {

    //TODO parâmetros - criar objetos

    @RequestMapping(method = RequestMethod.POST, value = "/transcript-to-list")
    List<FoodDTO> convertTranscriptToList(@RequestBody String transcript);

    @RequestMapping(method = RequestMethod.POST, value = "/find-food-items")
    List<FoodItemDTO> findFoodItems(@RequestBody List<FoodDTO> foodDTOs);
}
