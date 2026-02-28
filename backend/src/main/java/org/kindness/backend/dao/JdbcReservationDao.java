package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public final class JdbcReservationDao implements BaseDao<Reservation> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Reservation> mapper = (rs, _) -> {
        var res = new Reservation();
        res.setReservationId(rs.getLong("reservation_id"));
        res.setTableId(rs.getLong("table_id"));
        res.setUserId(rs.getLong("user_id"));
        res.setReservationStart(rs.getTimestamp("reservation_start").toLocalDateTime());
        res.setReservationEnd(rs.getTimestamp("reservation_end").toLocalDateTime());
        return res;
    };

    private static final String INSERT_QUERY = "INSERT INTO \"reservation\"(table_id, user_id, reservation_start, reservation_end) VALUES(?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"reservation\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"reservation\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"reservation\" WHERE id=?";

    @Override
    public void insert(Reservation model) {
        jdbcTemplate.update(INSERT_QUERY, model.getTableId(), model.getUserId(),
                model.getReservationStart(), model.getReservationEnd());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper).stream().findFirst();
    }
}
