package org.kindness.api.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    void insert(T model);
    void deleteById(Long id);
    List<T> findAll();
    Optional<T> findById(Long id);
}