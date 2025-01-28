package br.com.roselabs.food_base_meus_macros.clients;

import br.com.roselabs.food_base_meus_macros.configs.SecurityConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ai-meus-macros", configuration = SecurityConfig.class)
public interface AIClient {
    @RequestMapping(method = RequestMethod.POST, value = "/generate-embedding")
    List<Double> generateEmbedding(@RequestBody String foodName);

    @RequestMapping(method = RequestMethod.POST, value = "/choose")
    String chooseId(@RequestBody String json);
}
