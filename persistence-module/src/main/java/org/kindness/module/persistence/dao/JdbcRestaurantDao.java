package org.kindness.module.persistence.dao;

import lombok.RequiredArgsConstructor;
import org.kindness.common.dao.BaseDao;
import org.kindness.common.model.impl.Restaurant;
import org.kindness.common.model.impl.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class JdbcRestaurantDao implements BaseDao<Restaurant> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Restaurant> mapper = (rs, _) -> Restaurant.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .address(rs.getString("address"))
            .phone(rs.getString("phone"))
            .email(rs.getString("email"))
            .openTime(rs.getTime("open_time").toLocalTime())
            .closeTime(rs.getTime("close_time").toLocalTime())
            .dateCreated(rs.getTimestamp("date_created").toLocalDateTime())
            .dateUpdated(rs.getTimestamp("date_updated").toLocalDateTime())
            .isDeleted(rs.getBoolean("is_deleted"))
            .build();

    private static final String INSERT_QUERY = "INSERT INTO \"restaurants\"(name, address, phone, email, open_time, close_time) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "UPDATE \"restaurants\" SET is_deleted = TRUE WHERE id = ?";;
    private final static String FIND_ALL_QUERY = "SELECT * FROM \"restaurants\" where is_deleted = false";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM \"restaurants\" WHERE id=? and is_deleted = false";
    private final static String FIND_BY_ADDRESS = "SELECT * FROM \"restaurants\" where address = ? and is_deleted = false";

    @Override
    public void insert(Restaurant model) {
        jdbcTemplate.update(INSERT_QUERY, model.getName(), model.getAddress(),
                model.getPhone(), model.getEmail(),
                model.getOpenTime(), model.getCloseTime());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    @Override
    public List<Restaurant> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, mapper, id)
                .stream().findFirst();
    }

    public Optional<Restaurant> findByAddress(String address){
        return jdbcTemplate.query(FIND_BY_ADDRESS, mapper, address)
                .stream().findFirst();
    }
}
