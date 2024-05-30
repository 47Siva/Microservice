package com.example.MainServices.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info =@Info(
		contact = @Contact(
				name = "",
				email = "",
				url ="http://localhost:8080"
				),
		description = "MICRO SERVICE",
		title = "Micro Service Management System",
		version = "1.0",
		license = @License(name = "Licence name",
		                   url = "https//some-url.com"),
		termsOfService = "terms and service"),
		servers = {
		@Server(description ="http",
				url ="http://localhost:8080")
		}
)
public class SwaggerConfiguration {

}
