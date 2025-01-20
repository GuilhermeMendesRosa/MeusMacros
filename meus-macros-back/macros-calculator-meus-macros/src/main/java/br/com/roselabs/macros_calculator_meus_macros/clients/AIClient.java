package br.com.roselabs.macros_calculator_meus_macros.clients;

import br.com.roselabs.macros_calculator_meus_macros.dtos.FoodDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ai-meus-macros")
public interface AIClient {
    @RequestMapping(method = RequestMethod.POST, value = "/transcript-to-list")
    List<FoodDTO> convertTranscriptToList(@RequestBody String transcript);
}
