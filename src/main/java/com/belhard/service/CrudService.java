package com.belhard.service;

import java.util.List;

public interface CrudService<K, T> {
    T create(T dto);

    T get(K id);

    List<T> getAll();

    int countAll();

    T update(T dto);

    void delete(K id);
}
