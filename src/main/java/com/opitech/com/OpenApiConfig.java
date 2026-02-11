/**
 * 
 */
package com.opitech.com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author psych
 *
 */
@Configuration
public class OpenApiConfig {

	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Api de Heroes")
	                        .description("Ejemplo de API REST documentada con Springdoc")
	                        .version("1.0.0")
	                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	                .externalDocs(new ExternalDocumentation()
	                        .description("Documentación completa")
	                        .url("https://mi-docs.com"));
	    }
}
