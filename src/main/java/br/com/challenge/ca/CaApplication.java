package br.com.challenge.ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class CaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaApplication.class, args);
	}
}
