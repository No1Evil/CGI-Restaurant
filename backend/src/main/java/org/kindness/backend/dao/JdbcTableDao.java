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

    private static final RowMapper<Table> mapper = (rs, _) -> {
        var table = new Table();
        table.setTableId(rs.getLong("table_id"));
        table.setZoneId(rs.getLong("zone_id"));
        table.setCapacity(rs.getInt("capacity"));
        table.setPos_x(rs.getFloat("pos_x"));
        table.setPos_y(rs.getFloat("pos_y"));
        return table;
    };

    private static final String INSERT_QUERY = "INSERT INTO \"table\"(zone_id, capacity, pos_x, pos_y) VALUES(?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"table\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"table\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"table\" WHERE id=?";

    @Override
    public void insert(Table model) {
        jdbcTemplate.update(INSERT_QUERY, model.getZoneId(), model.getCapacity(), model.getPos_x(), model.getPos_y());
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
}
