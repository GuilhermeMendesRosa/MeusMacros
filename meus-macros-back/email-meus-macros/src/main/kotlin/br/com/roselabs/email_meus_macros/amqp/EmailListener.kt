package br.com.roselabs.email_meus_macros.amqp

import br.com.roselabs.email_meus_macros.dtos.ShowUserDTO
import br.com.roselabs.email_meus_macros.services.EmailService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class EmailListener(private val emailService: EmailService) {

    @RabbitListener(queues = ["email.bem-vindo"])
    fun receberMensagem(@Payload showUserDTO: ShowUserDTO) {
        this.emailService.sendWelcomeEmail(showUserDTO.email)
    }

}