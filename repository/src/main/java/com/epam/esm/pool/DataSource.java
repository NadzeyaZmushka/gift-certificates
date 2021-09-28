package com.epam.esm.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

    private static final HikariConfig config = new HikariConfig("/db.properties");
    private static final HikariDataSource dataSource = new HikariDataSource(config);

    private DataSource() {
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
