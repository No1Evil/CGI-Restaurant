package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcTableDao implements BaseDao<Table> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Table> mapper = (rs, rowNum) -> new Table(
            rs.getLong("id"),
            rs.getLong("zone_id"),
            rs.getInt("capacity"),
            rs.getFloat("pos_x"),
            rs.getFloat("pos_y"),
            rs.getTimestamp("date_created").toLocalDateTime(),
            rs.getTimestamp("date_updated").toLocalDateTime(),
            rs.getBoolean("is_deleted")
    );

    private static final String INSERT_QUERY = "INSERT INTO \"tables\"(zone_id, capacity, pos_x, pos_y) VALUES(?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"tables\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"tables\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"tables\" WHERE id=?";
    private final static String FIND_ALL_BY_ZONE_ID = "SELECT * from \"tables\" WHERE zone_id=?";
    private final static String COUNT_TABLES_IN_THE_ZONE =
                    "select t.*" +
                    "from \"zone\" z" +
                    "join \"table\" t on z.zone_id = t.zone_id";

    @Override
    public void insert(Table model) {
        //jdbcTemplate.update(INSERT_QUERY, model.getZoneId(), model.getCapacity(), model.getPos_x(), model.getPos_y());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    @Override
    public List<Table> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public Optional<Table> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper, id)
                .stream()
                .findFirst();
    }

    public List<Table> findAllByZoneId(Long zoneId){
        return jdbcTemplate.query(FIND_ALL_BY_ZONE_ID, mapper, zoneId);
    }
}
