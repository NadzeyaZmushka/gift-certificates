package com.epam.esm.config;

import com.epam.esm.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("com.epam.esm")
public class SpringConfiguration {

    @Bean
    public static JdbcTemplate getTemplate() {
        return new JdbcTemplate(DataSource.getDataSource());
    }

}
