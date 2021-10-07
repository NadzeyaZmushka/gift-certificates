package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.epam.esm")
@Import(DBConfiguration.class)
public class ServiceSpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
