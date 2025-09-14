package com.indomaret.backend.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Component
public class OpenApiConfig {
     @Bean
    public OpenAPI customerOpenApi(){
        return new OpenAPI()
                    .info(new Info()
                    .title("Indomaret Backend API")
                    .version("1.0"))
                .servers(List.of(new Server().url("http://localhost:8080")))
                
                .components(new Components()
                            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")))

                .security(List.of(new SecurityRequirement()
                            .addList("bearerAuth")));
    }
}
