package br.com.roselabs.email_meus_macros.amqp

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmailAMQPConfiguration {

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
        messageConverter: Jackson2JsonMessageConverter
    ): RabbitTemplate {
        return RabbitTemplate(connectionFactory).apply {
            this.messageConverter = messageConverter
        }
    }

    @Bean
    fun filaBemVindo(): Queue {
        return QueueBuilder
            .nonDurable("email.bem-vindo")
            .build()
    }

    @Bean
    fun fanoutExchange(): FanoutExchange {
        return ExchangeBuilder
            .fanoutExchange("email.ex")
            .build()
    }

    @Bean
    fun bindPagamentoPedido(): Binding {
        return BindingBuilder
            .bind(filaBemVindo())
            .to(fanoutExchange())
    }

    @Bean
    fun criaRabbitAdmin(conn: ConnectionFactory): RabbitAdmin {
        return RabbitAdmin(conn)
    }

    @Bean
    fun inicializaAdmin(rabbitAdmin: RabbitAdmin): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener { rabbitAdmin.initialize() }
    }
}
