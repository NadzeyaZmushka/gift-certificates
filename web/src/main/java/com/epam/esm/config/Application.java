package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableWebMvc
@SpringBootApplication
//@Import(ServiceSpringConfig.class)
//@ComponentScan(basePackages = "com.epam.esm")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

