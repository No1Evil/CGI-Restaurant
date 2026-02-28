package org.kindness.api.dao;

import org.kindness.api.model.Author;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    void insert(T model);
    void deleteById(Long id);
    List<Author> findAll();
    Optional<Author> findById(Long id);
}