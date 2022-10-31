package com.belhard.service;

import java.util.List;
import java.util.Locale;

import com.belhard.controller.util.PagingUtil.Paging;

public interface CrudService<K, T> {
	
	T create(T dto, Locale locale);

	T get(K id, Locale locale);

	List<T> getAll(Locale locale);

	long countAll(Locale locale);

	T update(T dto, Locale locale);

	void delete(K id, Locale locale);

	List<T> getAll(Paging paging, Locale locale);
}
