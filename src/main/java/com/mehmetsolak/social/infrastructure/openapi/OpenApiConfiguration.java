package com.mehmetsolak.social.infrastructure.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Social Feed API")
                        .description("Social Feed REST API Documentation")
                        .version("1.0.0"));
    }

    @Bean
    public OpenApiCustomizer logoutEndpointCustomizer() {
        return openApi -> openApi.path("/api/v1/auth/logout",
                new PathItem().post(new Operation()
                        .tags(List.of("Authentication"))
                        .operationId("logout")
                        .summary("Logout")
                        .description("Invalidates the session and clears the JSESSIONID cookie.")
                        .responses(new ApiResponses()
                                .addApiResponse("204", new ApiResponse().description("Logged Out")))));
    }
}
