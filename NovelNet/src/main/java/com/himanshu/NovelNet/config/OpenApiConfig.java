package com.himanshu.NovelNet.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Himanshu",
                        email = "hy95627@gmail.com"
                ),
                description = "Open Api Documentation for NovelNet",
                title = "Open API specification",
                version = "1.0.0",
                license = @License(
                        name = "license",
                        url = "https://localhost:someurl.com"
                ),
                termsOfService = "term of service"
        ),

        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080/api.v1"
                )
        },

        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(name = "bearerAuth",
        description = "Authentication schema configuration",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
