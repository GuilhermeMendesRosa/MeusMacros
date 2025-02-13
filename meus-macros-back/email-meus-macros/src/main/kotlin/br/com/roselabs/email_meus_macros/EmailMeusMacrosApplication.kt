package br.com.roselabs.email_meus_macros

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class EmailMeusMacrosApplication

fun main(args: Array<String>) {
	runApplication<EmailMeusMacrosApplication>(*args)
}
