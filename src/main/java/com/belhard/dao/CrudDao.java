package com.belhard.dao;

import java.util.List;

public interface CrudDao<K, T> {
    T create(T entity);

    T get(K id);

    List<T> getAll();

    int countAll();

    T update(T entity);

    boolean delete(K id);
}
