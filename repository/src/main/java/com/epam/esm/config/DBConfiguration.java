package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
public class DBConfiguration {
//
//    @Bean
//    public JdbcTemplate getTemplate() {
//        return new JdbcTemplate(getDatasource());
//    }
//
//    @Bean
//    @Profile("prod")
//    public HikariDataSource getDatasource() {
//        HikariConfig config = new HikariConfig("/db.properties");
//        return new HikariDataSource(config);
//    }
//
//    @Bean
//    @Profile("dev")
//    public DataSource embeddedDatasource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("/create_db.sql")
//                .build();
//    }

}
