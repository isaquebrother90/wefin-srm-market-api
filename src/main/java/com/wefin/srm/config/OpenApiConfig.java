package com.wefin.srm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wefin SRM Market API")
                        .version("1.0")
                        .description("API para o sistema de conversão de moedas do Mercado de Pulgas dos Mil Saberes.")
                        .termsOfService("http://swagger.io/terms/" )
                        .license(new License().name("Apache 2.0").url("http://springdoc.org" )));
    }
}
