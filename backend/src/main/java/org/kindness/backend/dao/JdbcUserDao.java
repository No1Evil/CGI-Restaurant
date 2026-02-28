package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcUserDao implements BaseDao<User> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> mapper = (rs, _) -> {
        var user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setSecondName(rs.getString("second_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setPasswordHash(rs.getString("password_hash"));
        return user;
    };

    private static final String INSERT_QUERY = "INSERT INTO \"user\"(firstName, secondName, email, password_hash, role) VALUES(?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"user\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"user\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id=?";

    @Override
    public void insert(User model) {
        jdbcTemplate.update(INSERT_QUERY, model.getFirstName(), model.getSecondName(), model.getEmail(), model.getPasswordHash(), model.getRole());
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
