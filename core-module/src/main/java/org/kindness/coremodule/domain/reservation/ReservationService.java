package org.kindness.coremodule.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.Reservation;
import org.kindness.common.model.impl.Restaurant;
import org.kindness.common.model.impl.Table;
import org.kindness.module.persistence.dao.impl.JdbcReservationDao;
import org.kindness.module.persistence.dao.impl.JdbcRestaurantDao;
import org.kindness.module.persistence.dao.impl.JdbcTableDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReservationService {
    private JdbcRestaurantDao restaurantDao;
    private JdbcReservationDao reservationDao;
    private JdbcTableDao tableDao;

    @Transactional
    public void createReservation(Reservation res) throws IllegalStateException {
        if (res.getReservationStart().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Cant reserve before now");
        }

        if (reservationDao.isTimeTaken(res)){
            throw new IllegalStateException("The time is taken");
        }

        Table table = tableDao.findById(res.getTableId()).orElseThrow(() ->
                new IllegalStateException("No such table"));

        Restaurant restaurant = restaurantDao.findById(table.getRestaurantId()).orElseThrow(() ->
                new IllegalStateException("No such restaurant"));

        if(restaurant.getOpenTime().isBefore(LocalTime.now())
                && restaurant.getCloseTime().isAfter(LocalTime.now())){
            throw new IllegalStateException("The restaurant is not open at that time");
        }

        reservationDao.insert(res);
    }

    @Transactional
    public void cancelReservation(Long reservationId){
        var savedRes = reservationDao.findById(reservationId).orElseThrow(() ->
                new IllegalStateException("No such reservation found"));
        reservationDao.deleteById(reservationId);
    }

}
