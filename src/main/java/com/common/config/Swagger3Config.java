package com.common.config;

import java.util.Arrays;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class Swagger3Config {
    @Bean
    public GroupedOpenApi publicApi() {
    	
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
    	SecurityScheme securityScheme = new SecurityScheme()
    			.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
    			.in(SecurityScheme.In.HEADER).name("Authorization");
    	SecurityRequirement schemaRequirement = new SecurityRequirement().addList("bearerAuth");
    	
    	
        return new OpenAPI()
        		.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        		.security(Arrays.asList(schemaRequirement))
                .info(new Info().title("아이디어스")
                        .description("CodingTest API")
                        .version("v0.0.1"));
    }
}
