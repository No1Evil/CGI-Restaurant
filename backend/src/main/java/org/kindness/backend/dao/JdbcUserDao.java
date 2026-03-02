package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.common.dao.BaseDao;
import org.kindness.common.model.impl.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcUserDao implements BaseDao<User> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> mapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("second_name"),
            rs.getString("email"),
            rs.getString("password_hash"),
            rs.getTimestamp("date_created").toLocalDateTime(),
            rs.getString("role"),
            rs.getBoolean("is_deleted")
    );

    private static final String INSERT_QUERY = "INSERT INTO \"users\"(first_name, second_name, email, password_hash, role) VALUES(?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "UPDATE \"users\" SET is_deleted = TRUE WHERE id = ?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"users\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"users\" WHERE id=?";

    @Override
    public void insert(User model) {
        jdbcTemplate.update(INSERT_QUERY,
                model.getFirstName(), model.getSecondName(),
                model.getEmail(), model.getPasswordHash(), model.getRole());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper).stream().findFirst();
    }
}
