package org.kindness.coremodule.domain.user;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.User;
import org.kindness.module.persistence.dao.impl.JdbcUserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistrationService {
    private final JdbcUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String email, String password, String firstName, String secondName){
        if (isEmailBusy(email)) throw new IllegalStateException("Email already taken");
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .firstName(firstName)
                .lastName(secondName)
                .build();

        userDao.insert(user);

        return userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Failed to retrieve user after registration"));
    }

    public boolean isEmailBusy(String email){
        return userDao.findByEmail(email).isPresent();
    }
}
