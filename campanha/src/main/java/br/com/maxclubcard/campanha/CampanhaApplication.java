package br.com.maxclubcard.campanha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CampanhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampanhaApplication.class, args);
	}

}
