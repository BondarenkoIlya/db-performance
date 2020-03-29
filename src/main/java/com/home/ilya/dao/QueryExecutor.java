package com.home.ilya.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryExecutor {

    private final Map<String, JdbcTemplate> templates;

    public void execute(String query, String database) {
        templates.get(database).execute(query);
    }
}
