package com.belhard.service;

import java.util.List;

import com.belhard.controller.util.PagingUtil.Paging;

public interface CrudService<K, T> {
	T create(T dto);

	T get(K id);

	List<T> getAll();

	long countAll();

	T update(T dto);

	void delete(K id);

	List<T> getAll(Paging paging);
}
