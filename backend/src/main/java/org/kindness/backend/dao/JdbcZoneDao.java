package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Zone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcZoneDao implements BaseDao<Zone> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Zone> mapper = (rs, _) -> {
        var zone = new Zone();
        zone.setZoneId(rs.getLong("zone_id"));
        zone.setName(rs.getString("name"));
        zone.setCapacity(rs.getInt("capacity"));
        return zone;
    };

    private static final String INSERT_QUERY = "INSERT INTO \"zone\"(name, capacity) VALUES(?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"zone\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"zone\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"zone\" WHERE id=?";

    @Override
    public void insert(Zone model) {
        jdbcTemplate.update(INSERT_QUERY, model.getName(), model.getCapacity());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    @Override
    public List<Zone> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public Optional<Zone> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper)
                .stream()
                .findFirst();
    }
}
