package org.kindness.module.persistence.service;

import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.Reservation;
import org.kindness.module.persistence.dao.JdbcReservationDao;
import org.kindness.module.persistence.dao.JdbcTableDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
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
