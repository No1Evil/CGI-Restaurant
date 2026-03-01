package org.kindness.backend.service;

import lombok.RequiredArgsConstructor;
import org.kindness.api.model.impl.Reservation;
import org.kindness.backend.dao.JdbcReservationDao;
import org.kindness.backend.dao.JdbcTableDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final JdbcReservationDao reservationDao;
    private final JdbcTableDao tableDao;

    @Transactional
    public void createReservation(Reservation res){
        if (res.getReservationStart().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Cant reserve before now");
        }

        if (reservationDao.isTimeTaken(res)){
            throw new IllegalStateException("The time is taken");
        }

        reservationDao.insert(res);
    }

    @Transactional
    public void cancelReservation(Reservation res){
        Long id = res.getId();
        var savedRes = reservationDao.findById(id).orElseThrow(IllegalStateException::new);
        reservationDao.deleteById(id);
    }
}
