package br.com.roselabs.macros_calculator_meus_macros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MacrosCalculatorMeusMacrosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MacrosCalculatorMeusMacrosApplication.class, args);
    }

}
