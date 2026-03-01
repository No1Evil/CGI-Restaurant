package org.kindness.backend.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Table;
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

    private static final RowMapper<Zone> mapper = (rs, rowNum) -> new Zone(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getTimestamp("date_created").toLocalDateTime(),
            rs.getTimestamp("date_updated").toLocalDateTime(),
            rs.getBoolean("is_deleted")
    );

    private static final String INSERT_QUERY = "INSERT INTO \"zones\"(name, date_created, date_updated) VALUES(?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM \"zones\" WHERE id=?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"zones\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"zones\" WHERE id=?";

    @Override
    public void insert(Zone model) {
        jdbcTemplate.update(INSERT_QUERY, model.getName());
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

//    public List<Table> findTablesInZone(Long zoneId){
//        return jdbcTemplate.query(FIND_TABLES_WITHIN_ZONE, )
//    }
}
