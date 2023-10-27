package com.di2win.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Account")
                        .description("Simulação de um banco")
                        .contact(new Contact()
                                .name("Elton Henrique")
                                .email("eltonhst34@gmail.com"))
                        .version("1.0.0"));
    }
}
