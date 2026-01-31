package com.example.produtoapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI produtoOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Produto API - Content Negotiation")
                .description("API REST MVC para gerenciamento de produtos com suporte a JSON, XML e YAML")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Equipe de Desenvolvimento")
                    .email("dev@example.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
