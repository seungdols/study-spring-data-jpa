package com.seungdols.dev.datajpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional
import java.util.UUID

@EnableJpaAuditing
@SpringBootApplication
class DataJpaApplication {
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            Optional.of(UUID.randomUUID().toString())
        }
    }
}

fun main(args: Array<String>) {
	runApplication<DataJpaApplication>(*args)
}
