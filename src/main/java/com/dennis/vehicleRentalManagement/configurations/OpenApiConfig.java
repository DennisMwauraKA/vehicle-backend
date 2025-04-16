package com.dennis.vehicleRentalManagement.configurations;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(

                contact = @Contact(
                        name = "Dennis",
                        email = "kariukidennis@gmail.com"

                ),
                description = "Open Api documentation for VehicleRentalManagementApplication",
                title = "Vehicle Rental Management API Specification",
                version = "1.0",
                license = @License(
                        name = "license",
                        url = "https://rem.mit-license.org/+MIT"
                ),
                termsOfService = "terms of service"
        ),
        servers =
                {
                        @Server(
                                description = "Local env",
                                url = "http://localhost:8000/api/v1"
                        )
                },
        security = {

                @SecurityRequirement(
                        name = "bearerAuth"

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
