package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public final class JdbcReservationDao implements BaseDao<Reservation> {
    private final JdbcTemplate jdbcTemplate;

    public final RowMapper<Reservation> mapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("table_id"),
            rs.getTimestamp("reservation_start").toLocalDateTime(),
            rs.getTimestamp("reservation_end").toLocalDateTime(),
            rs.getTimestamp("date_created").toLocalDateTime(),
            rs.getTimestamp("date_updated").toLocalDateTime(),
            rs.getBoolean("is_deleted")
    );

    private static final String INSERT_QUERY =
            "INSERT INTO \"reservations\"" +
            "(user_id, table_id, reservation_start, reservation_end, date_created, date_updated, is_deleted) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"reservations\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"reservations\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"reservations\" WHERE id=?";
    private final static String IS_TIME_TAKEN =
            "SELECT COUNT(*) FROM \"reservations\" " +
            "WHERE table_id = ? " +
            "AND (reservation_start < ? and reservation_end > ?)";

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

    public boolean isTimeTaken(long table_id, LocalDateTime start, LocalDateTime end){
        var value = jdbcTemplate.queryForObject(IS_TIME_TAKEN, Integer.class, mapper, table_id, end, start);
        return value != null && value > 0;
    }
}
