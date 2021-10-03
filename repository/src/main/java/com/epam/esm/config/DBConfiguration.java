package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("com.epam.esm")
public class DBConfiguration {

    @Bean
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDatasource());
    }

    @Bean
    public HikariDataSource getDatasource() {
        HikariConfig config = new HikariConfig("/db.properties");
        return new HikariDataSource(config);
    }

}
