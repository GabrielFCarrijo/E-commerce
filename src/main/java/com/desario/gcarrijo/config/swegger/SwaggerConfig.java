package com.desario.gcarrijo.config.swegger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio Técnico - E-Commerce")
                        .version("1.0")
                        .description("Documentação da API do e-commerce"));
    }
}