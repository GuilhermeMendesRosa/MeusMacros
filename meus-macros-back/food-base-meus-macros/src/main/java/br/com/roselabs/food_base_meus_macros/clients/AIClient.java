package br.com.roselabs.food_base_meus_macros.clients;

import org.springframework.cloud.openfeign.FeignClient;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ai-meus-macros")
public interface AIClient {
    @RequestMapping(method = RequestMethod.POST, value = "/generate-embedding")
    List<Double> generateEmbedding(@RequestBody String foodName);
}
