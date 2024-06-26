package br.com.maxclubcard.campanhas.config.swagger;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.service.Contact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");}

  public static final Contact DEFAULT_CONTACT = new Contact(
      "Jeronima Antonia Floriano", "https://br.linkedin.com/in/jeronimafloriano", "jeronimajc@hotmail.com");

  public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
      "API MaxClubCard", "Api para Cadastro de Campanha MaxClubCard", "1.0",
      "urn:tos", DEFAULT_CONTACT,
      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("maxclubcard").select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .apiInfo(DEFAULT_API_INFO)
        .enableUrlTemplating(false);
  }
}