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

import javax.swing.plaf.PanelUI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final JdbcRestaurantDao restaurantDao;
    private final JdbcReservationDao reservationDao;
    private final JdbcTableDao tableDao;

    @Transactional
    public Long createReservation(Reservation res) throws IllegalStateException {
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

        LocalTime startTime = res.getReservationStart().toLocalTime();
        LocalTime endTime = res.getReservationEnd().toLocalTime();

        if (startTime.isBefore(restaurant.getOpenTime()) || endTime.isAfter(restaurant.getCloseTime())) {
            throw new IllegalStateException("Restaurant is not open at that time");
        }

        return reservationDao.insertAndGetID(res);
    }

    @Transactional
    public void cancelReservation(Long reservationId, Long userId){
        var reservation = reservationDao.findById(reservationId)
                .orElseThrow(() -> new IllegalStateException("Reservation not found"));

        if (!reservation.getUserId().equals(userId)) {
            throw new IllegalStateException("You cant access that reservation");
        }

        reservationDao.deleteById(reservationId);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllUserReservations(Long userId) {
        return reservationDao.findAll(userId);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations(){
        return reservationDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> getUserReservation(Long reservationId, Long userId){
        return reservationDao.findById(reservationId, userId);
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> getReservation(Long reservationId){
        return reservationDao.findById(reservationId);
    }

}
