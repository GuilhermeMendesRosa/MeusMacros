package br.com.roselabs.auth_meus_macros.clients;

import br.com.roselabs.auth_meus_macros.configs.SecurityConfig;
import br.com.roselabs.auth_meus_macros.data.GoalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "macros-calculator-meus-macros", configuration = SecurityConfig.class)
public interface MacrosCalculatorClient {

    @PostMapping("/goals")
    GoalDTO generate(@RequestBody GoalDTO goalDTO);
}
