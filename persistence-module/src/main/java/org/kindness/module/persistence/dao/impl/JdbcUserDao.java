package org.kindness.module.persistence.dao.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.User;
import org.kindness.common.model.impl.UserRestaurantPermission;
import org.kindness.module.persistence.dao.BaseDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcUserDao implements BaseDao<User> {
    private final JdbcTemplate jdbcTemplate;

    // https://www.baeldung.com/spring-classpath-file-access
    @Value("classpath:sql/scripts/update_restaurant_role.sql")
    private Resource updateRestaurantRoleScript;

    @PostConstruct
    public void init() throws IOException {
        UPDATE_RESTAURANT_ROLE = new String(updateRestaurantRoleScript.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private static final RowMapper<User> mapper = (rs, _) -> User.builder()
            .userId(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .role(rs.getString("role"))
            .applyBaseFields(rs)
            .build();

    private static final String INSERT_QUERY = "INSERT INTO \"users\"(first_name, last_name, email, password, role) VALUES(?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "UPDATE \"users\" SET is_deleted = TRUE WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM \"users\" where is_deleted = false";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM \"users\" WHERE id=? and is_deleted = false";
    private static final String FIND_BY_EMAIL = "SELECT * FROM \"users\" WHERE email=? and is_deleted = false";
    private static final String GET_USER_PERMISSIONS_QUERY = "SELECT * FROM \"user_restaurant_permissions\" WHERE user_id = ?";
    private static final String UPDATE_GLOBAL_ROLE_QUERY = "UPDATE \"users\" SET role = ? WHERE id = ? and is_deleted = false";
    private static String UPDATE_RESTAURANT_ROLE;

    @Override
    public void insert(User model) {
        jdbcTemplate.update(INSERT_QUERY,
                model.getFirstName(), model.getLastName(),
                model.getEmail(), model.getPassword(), model.getRole());
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
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper, id).stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(FIND_BY_EMAIL, mapper, email)
                .stream().findFirst();
    }

    public void fillWithPermissions(User user) {
        List<UserRestaurantPermission> perms = jdbcTemplate.query(GET_USER_PERMISSIONS_QUERY, (rs, rowNum) ->
                new UserRestaurantPermission(
                        rs.getLong("user_id"),
                        rs.getLong("restaurant_id"),
                        rs.getString("role")
                ), user.getUserId());
        user.setPermissions(perms);
    }

    public void updateGlobalRole(Long userId, String role) {
        jdbcTemplate.update(UPDATE_GLOBAL_ROLE_QUERY, role, userId);
    }

    public void updateRestaurantRole(Long userId, Long restaurantId, String role){
        jdbcTemplate.update(UPDATE_RESTAURANT_ROLE, userId, restaurantId, role);
    }
}
