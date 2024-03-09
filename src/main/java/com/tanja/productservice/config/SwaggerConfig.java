package com.tanja.productservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);
    @Bean
    public OpenAPI baseOpenAPI() {
        LOGGER.info("Configuring OpenAPI");
        return new OpenAPI().info(
                new Info()
                        .title("Product API")
                        .version("1.0.0")
                        .description("Spring documentation for Product API.")
        );
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        LOGGER.info("Add view controllers");
        registry.addRedirectViewController("/docs", "/swagger-ui.html");
    }

}
