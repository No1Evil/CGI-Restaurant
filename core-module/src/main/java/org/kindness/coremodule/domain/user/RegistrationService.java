package org.kindness.coremodule.domain.user;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.User;
import org.kindness.module.persistence.dao.impl.JdbcUserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistrationService {
    private final JdbcUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String email, String password, String firstName, String secondName){
        if (isEmailBusy(email)) throw new IllegalStateException("Email is already taken");
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .passwordHash(encodedPassword)
                .firstName(firstName)
                .secondName(secondName)
                .build();

        userDao.insert(user);

        return user;
    }

    public boolean isEmailBusy(String email){
        return userDao.findByEmail(email).isPresent();
    }
}
