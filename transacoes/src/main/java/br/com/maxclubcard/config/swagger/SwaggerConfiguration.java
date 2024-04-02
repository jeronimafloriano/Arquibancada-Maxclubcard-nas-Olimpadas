package br.com.maxclubcard.config.swagger;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  public static final Contact DEFAULT_CONTACT = new Contact(
      "Jeronima Antonia Floriano", "https://br.linkedin.com/in/jeronimafloriano",
      "jeronimajc@hotmail.com");

  public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
      "API MaxClubCard", "Api para Registro de Transacoes de Pagamento com MaxClubCard", "1.0",
      "urn:tos", DEFAULT_CONTACT,
      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .apiInfo(DEFAULT_API_INFO)
        .globalRequestParameters(Collections.singletonList(
            new RequestParameterBuilder()
                .name("Authorization")
                .description("JWT token")
                .in(ParameterType.HEADER)
                .required(false)
                .build()));
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
        .scopeSeparator(",")
        .additionalQueryStringParams(null)
        .build();
  }
}
