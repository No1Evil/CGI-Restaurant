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
        User user = userDao.findById(id).orElseThrow(() -> new IllegalStateException("No such user"));
        if (isPrivileged(user.getGlobalRole())) userDao.fillWithPermissions(user);
        return user;
    }

    @Transactional
    public User changeGlobalRole(Long userId, String newRole) {
        userDao.updateGlobalRole(userId, newRole);
        return getUserData(userId);
    }

    @Transactional
    public User changeRestaurantRole(Long userId, Long restaurantId, String newRole) {
        userDao.updateRestaurantRole(userId, restaurantId, newRole);
        return getUserData(userId);
    }

    public void deleteUser(Long userId){
        userDao.deleteById(userId);
    }

    private boolean isPrivileged(String role) {
        return !"USER".equalsIgnoreCase(role);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }
}
