package org.kindness.backend.dao;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.impl.Table;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcTableDao implements BaseDao<Table> {
    private final JdbcTemplate jdbcTemplate;

    // https://www.baeldung.com/spring-classpath-file-access
    @Value("classpath:sql/scripts/find_available_tables.sql")
    private Resource findAvailableTablesScript;

    @PostConstruct
    public void init() throws IOException {
        FIND_AVAILABLE_TABLES_QUERY = new String(findAvailableTablesScript.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

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
    private static final String REMOVE_QUERY = "UPDATE \"tables\" SET is_deleted = TRUE WHERE id = ?";;
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"tables\"";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"tables\" WHERE id=?";
    private final static String FIND_ALL_BY_ZONE_ID_QUERY = "SELECT * from \"tables\" WHERE zone_id=?";
    private static String FIND_AVAILABLE_TABLES_QUERY;

    @Override
    public void insert(Table model) {
        jdbcTemplate.update(INSERT_QUERY, model.getZoneId(), model.getCapacity(), model.getPosX(), model.getPosY());
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

    /**
     * @return all available tables within startTime and endTime
     */
    public List<Table> findAllAvailable(@Nullable Long zoneId, @Nullable Integer capacity, Timestamp startTime, Timestamp endTime) {
        return jdbcTemplate.query(FIND_AVAILABLE_TABLES_QUERY, mapper,
                zoneId, zoneId, capacity, capacity,
                endTime, startTime);
    }

    public List<Table> findAllByZoneId(Long zoneId) {
        return jdbcTemplate.query(FIND_ALL_BY_ZONE_ID_QUERY, mapper, zoneId);
    }
}
