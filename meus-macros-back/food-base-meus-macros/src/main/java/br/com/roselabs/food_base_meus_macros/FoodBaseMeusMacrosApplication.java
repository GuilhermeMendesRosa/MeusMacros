package br.com.roselabs.food_base_meus_macros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoodBaseMeusMacrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodBaseMeusMacrosApplication.class, args);
	}

}
