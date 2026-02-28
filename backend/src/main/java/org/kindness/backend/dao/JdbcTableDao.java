package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class JdbcTableDao {
    private final JdbcTemplate jdbcTemplate;

}
