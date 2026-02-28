package org.kindness.backend.mapper;


import org.kindness.api.model.impl.Table;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TableRowMapper implements RowMapper<Table> {
    @Override
    public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table table = new Table();
        table.setTableId(rs.getLong("table_id"));
        table.setZoneId(rs.getLong("zone_id"));
        table.setCapacity(rs.getInt("capacity"));
        table.setPos_x(rs.getFloat("pos_x"));
        table.setPos_y(rs.getFloat("pos_y"));
        return table;
    }
}
