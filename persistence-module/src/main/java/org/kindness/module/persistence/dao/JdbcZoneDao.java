package org.kindness.module.persistence.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.common.dao.BaseDao;
import org.kindness.common.model.impl.Zone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcZoneDao implements BaseDao<Zone> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Zone> mapper = (rs, rowNum) -> Zone.builder()
            .zoneId(rs.getLong("id"))
            .name(rs.getString("name"))
            .dateCreated(rs.getTimestamp("date_created").toLocalDateTime())
            .dateUpdated(rs.getTimestamp("date_updated").toLocalDateTime())
            .isDeleted(rs.getBoolean("is_deleted"))
            .build();

    private static final String INSERT_QUERY = "INSERT INTO \"zones\"(name) VALUES(?)";
    private static final String REMOVE_QUERY = "UPDATE \"zones\" SET is_deleted = TRUE WHERE id = ?";
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"zones\" where is_deleted = false";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"zones\" WHERE id=? and is_deleted = false";

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
}
