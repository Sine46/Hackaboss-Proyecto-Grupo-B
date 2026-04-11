package com.hackaboss.Proyecto_1_Grupo_B;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Proyecto1GrupoBApplication {

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1GrupoBApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("Proyecto Grupo B")
				.version("1.0.0")
				.description("Documentacion oficial del Proyecto"));
	}
}
