package com.example.SpringProjectsManagement.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket ItemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.SpringProjectsManagement"))
                .paths(regex("/projects.*|/tasks.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot Swagger Example API",
                "spring-boot application with a REST controller, which exposes a Swagger API catalog.",
                "1.0",
                null,
                new Contact("Dor Shamir", "https://github.com/dorshamir55",
                        "dorshamir4@gmail.com"),
                                null, null
        );

        return apiInfo;
    }
}
