package org.kindness.module.persistence.dao.impl;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.User;
import org.kindness.module.persistence.dao.BaseDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcUserDao implements BaseDao<User> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> mapper = (rs, rowNum) -> User.builder()
            .userId(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .secondName(rs.getString("second_name"))
            .email(rs.getString("email"))
            .passwordHash(rs.getString("password_hash"))
            .dateCreated(rs.getTimestamp("date_created").toLocalDateTime())
            .dateUpdated(rs.getTimestamp("date_updated").toLocalDateTime())
            .isDeleted(rs.getBoolean("is_deleted"))
            .build();

    private static final String INSERT_QUERY = "INSERT INTO \"users\"(first_name, second_name, email, password_hash, role) VALUES(?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "UPDATE \"users\" SET is_deleted = TRUE WHERE id = ?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"users\" where is_deleted = false";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"users\" WHERE id=? and is_deleted = false";
    private final static String FIND_BY_EMAIL = "SELECT * FROM \"users\" WHERE email=? and is_deleted = false";

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

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(FIND_BY_EMAIL, mapper, email)
                .stream().findFirst();
    }
}
