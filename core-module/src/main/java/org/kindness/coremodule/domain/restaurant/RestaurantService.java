package org.kindness.coremodule.domain.restaurant;

import org.kindness.common.model.impl.Restaurant;
import org.kindness.module.persistence.dao.impl.JdbcRestaurantDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {
    private JdbcRestaurantDao restaurantDao;

    public Restaurant getRestaurant(Long restaurantId){
        if (restaurantId == null) throw new IllegalStateException();
        return restaurantDao.findById(restaurantId)
                .orElseThrow(() -> new IllegalStateException("No restaurant found"));
    }

    public List<Restaurant> getAllRestaurants(){
        return restaurantDao.findAll();
    }

    @Transactional
    public void createRestaurant(Restaurant restaurant){
        restaurantDao.insert(restaurant);
    }

    @Transactional
    public void removeRestaurant(Long restaurantId){
        if (restaurantId == null) throw new IllegalStateException();
        restaurantDao.deleteById(restaurantId);
    }
}
