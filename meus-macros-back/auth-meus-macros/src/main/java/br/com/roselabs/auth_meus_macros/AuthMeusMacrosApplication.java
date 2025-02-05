package br.com.roselabs.auth_meus_macros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthMeusMacrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMeusMacrosApplication.class, args);
	}

}
