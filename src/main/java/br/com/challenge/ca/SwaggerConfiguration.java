package br.com.challenge.ca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket endpoints() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("challenge.ca")
                .select()
                .apis(basePackage("br.com.challenge.ca.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Cliente REST API Boleto Facil",
                "O objetivo dessa api e disponibilizar uma maneira simples e segura para cuidar de toda a " +
                        "gestão de boletos de microempresas.",
                "Versao API " + "0.1",
                "Termos de uso",
                new Contact("Roberto Candido de Oliveira Júnior",
                        "https://github.com/robertocojr/challenge-ca",
                        "robertoco.jr@gmail.com"),
                "",
                "",
                new ArrayList<>()
        );
        return apiInfo;
    }

}
