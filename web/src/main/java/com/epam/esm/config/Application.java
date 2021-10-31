package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableWebMvc
@SpringBootApplication
//@Import(ServiceSpringConfig.class)
//@ComponentScan(basePackages = "com.epam.esm")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

