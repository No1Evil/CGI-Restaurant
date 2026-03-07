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
public class AuthenticationService {
    private final JdbcUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User login(String email, String password){
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("No such user found"));
        if (passwordEncoder.matches(password, user.getPasswordHash())){
            return user;
        }
        return null;
    }
}
