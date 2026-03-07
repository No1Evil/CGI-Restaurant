package org.kindness.module.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kindness.common.model.impl.User;
import org.kindness.module.persistence.dao.impl.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserTest {

    @Autowired
    private JdbcUserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void cleanup() {
        jdbcTemplate.execute("DELETE FROM \"reservations\"");
        jdbcTemplate.execute("DELETE FROM \"users\"");
    }

    @Test
    public void shouldFindUserByEmail() {
        String email = "testgm@gmail.com";
        User user = User.builder()
                .email(email)
                .firstName("tester")
                .secondName("dayum")
                .passwordHash("hashed_password")
                .build();
        userDao.insert(user);

        Optional<User> foundUser = userDao.findByEmail(email);

        assertThat(foundUser).isPresent();

        User actualUser = foundUser.get();
        assertThat(actualUser.getEmail()).isEqualTo(email);
        assertThat(actualUser.getFirstName()).isEqualTo("tester");
        assertThat(actualUser.getSecondName()).isEqualTo("dayum");

        assertThat(actualUser.getUserId()).isNotNull();
    }
}
