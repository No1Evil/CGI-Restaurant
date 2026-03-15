package org.kindness.module.persistence.dao.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kindness.common.model.impl.Reservation;
import org.kindness.common.model.util.TimestampConverter;
import org.kindness.module.persistence.dao.BaseDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public final class JdbcReservationDao implements BaseDao<Reservation> {
    private final JdbcTemplate jdbcTemplate;

    @Value("classpath:sql/scripts/is_time_taken.sql")
    private Resource isTimeTakenScript;

    @PostConstruct
    public void init() throws IOException {
        IS_TIME_TAKEN = new String(isTimeTakenScript.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public final RowMapper<Reservation> mapper = (rs, _) -> Reservation.builder()
            .id(rs.getLong("id"))
            .tableId(rs.getLong("table_id"))
            .userId(rs.getLong("user_id"))
            .startsAt(rs.getTimestamp("starts_at").toInstant())
            .endsAt(rs.getTimestamp("ends_at").toInstant())
            .applyBaseFields(rs)
            .build();

    private static final String INSERT_QUERY =
            "INSERT INTO \"reservations\"" +
            "(user_id, table_id, starts_at, ends_at) " +
            "VALUES(?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "UPDATE \"reservations\" SET is_deleted = TRUE WHERE id = ?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"reservations\" WHERE is_deleted = false AND ends_at > NOW()";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"reservations\" WHERE id=? and is_deleted = false AND ends_at > NOW();";
    private final static String FIND_BY_ID_AND_USER_ID_QUERY = "SELECT * FROM \"reservations\" WHERE id=? and user_id=? and is_deleted=false AND ends_at > NOW();";
    private final static String FIND_ALL_USER_RESERVATIONS_QUERY = "SELECT * FROM \"reservations\" WHERE user_id=? and is_deleted=false AND ends_at > NOW();";
    private static String IS_TIME_TAKEN;

    @Override
    public void insert(Reservation model) {
        jdbcTemplate.update(INSERT_QUERY,
                model.getTableId(), model.getUserId(),
                model.getStartsAt(), model.getEndsAt());
    }

    public long insertAndGetID(Reservation model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[] {"id"});
            ps.setLong(1, model.getUserId());
            ps.setLong(2, model.getTableId());
            ps.setTimestamp(3, Timestamp.from(model.getStartsAt()));
            ps.setTimestamp(4, Timestamp.from(model.getEndsAt()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
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
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper, id).stream().findFirst();
    }

    public Optional<Reservation> findById(Long id, Long userId){
        return jdbcTemplate.query(FIND_BY_ID_AND_USER_ID_QUERY, mapper, id, userId).stream().findFirst();
    }

    public List<Reservation> findAll(Long userId){
        return jdbcTemplate.query(FIND_ALL_USER_RESERVATIONS_QUERY, mapper, userId);
    }

    public boolean isTimeTaken(Reservation res){
        return this.isTimeTaken(res.getTableId(), res.getStartsAt(), res.getEndsAt());
    }

    public boolean isTimeTaken(long table_id, Instant start, Instant end){
        var value = jdbcTemplate.queryForObject(
                IS_TIME_TAKEN,
                Integer.class,
                table_id,
                Timestamp.from(end),
                Timestamp.from(start)
        );
        return value != null && value > 0;
    }
}
