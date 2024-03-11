package com.aliakseikul.storenew.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Aliaksei Kuliavets",
                        email = "kulevetsav@gmail.com"
                ),
                description = "OpenApi documentation for Store",
                title = "OpenApi specification - STORE demo project",
                version = "1.0 Beta",
                termsOfService = "/api/"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
