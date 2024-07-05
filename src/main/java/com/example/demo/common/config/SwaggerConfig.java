package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Study API")
                        .description("This is an api made to train java spring")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pablo Rosa")
                                .url("https://www.linkedin.com/in/pablo-rosa-68136a1b2/")
                                .email("pablorsantos15@gmail.com")))
                .schemaRequirement("Bearer Authentication",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer"));
    }
}
