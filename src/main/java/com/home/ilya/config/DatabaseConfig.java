package com.home.ilya.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource mssqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;DatabaseName=dbp");
        dataSource.setUsername("dbp");
        dataSource.setPassword("passworD1");
        return dataSource;
    }

    @Bean
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/dbp");
        dataSource.setUsername("dbp");
        dataSource.setPassword("passworD1");
        return dataSource;
    }

    @Bean
    public Map<String, JdbcTemplate> templates() {
        return Map.of(
                "mssql", new JdbcTemplate(mssqlDataSource()),
                "postgresql", new JdbcTemplate(postgresqlDataSource())
        );
    }

    @Bean
    public List<String> databases() {
        return List.of("postgresql", "mssql");
    }
}
