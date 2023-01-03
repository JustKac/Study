package br.com.segsat.restwhitspringbootandjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Restful API With Java 17 and Springboot 3")
                        .version("V1")
                        .description("Some Description")
                        .termsOfService("https://meu-host-site.com.br")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://meu-host-site.com.br")));
    }
}