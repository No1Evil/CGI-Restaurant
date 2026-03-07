package org.kindness.coremodule.domain.user;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.User;
import org.kindness.module.persistence.dao.impl.JdbcUserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final JdbcUserDao userDao;

    public User getUserData(Long id){
        if (id == null) throw new IllegalStateException("Id cannot be null");
        return userDao.findById(id).orElseThrow(() -> new IllegalStateException("No such user"));
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }
}
