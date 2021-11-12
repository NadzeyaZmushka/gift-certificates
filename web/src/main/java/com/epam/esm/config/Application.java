package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.epam.esm.**"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{Application.class, ServiceSpringConfig.class}, args);
    }

}

