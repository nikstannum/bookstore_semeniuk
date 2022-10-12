package com.belhard.dao;

import java.util.List;

public interface CrudRepository<K, T> {
    T create(T entity);

    T get(K id);

    List<T> getAll();

    long countAll();

    T update(T entity);

    boolean delete(K id);
    
    List<T> getAll(int limit, long offset);
    
}
