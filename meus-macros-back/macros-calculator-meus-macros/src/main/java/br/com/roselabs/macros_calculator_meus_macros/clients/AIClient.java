package br.com.roselabs.macros_calculator_meus_macros.clients;

import br.com.roselabs.macros_calculator_meus_macros.configs.SecurityConfig;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.macros_calculator_meus_macros.dtos.TranscriptDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ai-meus-macros", configuration = SecurityConfig.class)
public interface AIClient {

    @RequestMapping(method = RequestMethod.POST, value = "/transcript-to-list")
    List<FoodItemDTO> convertTranscriptToList(@RequestBody TranscriptDTO transcript);

}
