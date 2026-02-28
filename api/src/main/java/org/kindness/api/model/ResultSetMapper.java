package org.kindness.api.model;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultSetMapper<T> {
    T create(ResultSet rs);
}
