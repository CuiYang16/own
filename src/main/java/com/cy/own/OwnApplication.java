package com.cy.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class OwnApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnApplication.class, args);
    }

}
