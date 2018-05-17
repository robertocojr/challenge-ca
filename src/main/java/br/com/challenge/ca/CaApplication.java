package br.com.challenge.ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class CaApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Brazil/East"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CaApplication.class, args);
    }

}
