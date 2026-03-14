package org.kindness.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseModel {
    protected Instant createdAt;
    protected Instant updatedAt;
    protected boolean isDeleted;

    public abstract static class BaseModelBuilder<C extends BaseModel, B extends BaseModelBuilder<C, B>> {
        public B applyBaseFields(ResultSet rs) throws SQLException {
            return this.createdAt(rs.getObject("created_at", Instant.class))
                    .updatedAt(rs.getObject("updated_at", Instant.class))
                    .isDeleted(rs.getBoolean("is_deleted"));
        }
    }
}
