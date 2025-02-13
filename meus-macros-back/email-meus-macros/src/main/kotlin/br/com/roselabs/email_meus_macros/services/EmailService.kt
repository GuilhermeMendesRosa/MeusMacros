package br.com.roselabs.email_meus_macros.services

import jakarta.mail.MessagingException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendWelcomeEmail(toEmail: String) {
        val subject = "Bem-vindo ao Meus Macros!"
        val body = """
            <h1>Olá,</h1>
            <p>Obrigado por criar sua conta no <strong>Meus Macros</strong>!</p>
            <p>Esperamos que tenha uma ótima experiência acompanhando sua alimentação.</p>
            <br>
            <p>Atenciosamente,</p>
            <p><strong>Equipe Meus Macros</strong></p>
        """.trimIndent()

        sendEmail(toEmail, subject, body)
    }

    private fun sendEmail(to: String, subject: String, body: String) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(body, true)
            helper.setFrom("no-reply@meusmacros.com")

            mailSender.send(message)
        } catch (e: MessagingException) {
            throw RuntimeException("Erro ao enviar e-mail", e)
        }
    }
}