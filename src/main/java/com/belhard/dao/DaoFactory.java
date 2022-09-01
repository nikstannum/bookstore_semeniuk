package com.belhard.dao;

import java.util.HashMap;
import java.util.Map;

import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.dao.impl.UserDaoImpl;

public class DaoFactory {
	public static final DaoFactory INSTANCE = new DaoFactory();
	private final Map<Class<?>, CrudDao> map; // TODO: Should me parameterization here?

	private DaoFactory() {
		DataSource dataSource = DataSource.INSTANCE;
		map = new HashMap<>();
		map.put(BookDao.class, new BookDaoImpl(dataSource));
		map.put(UserDao.class, new UserDaoImpl(dataSource));
	}

	public <T> T getDao(Class<T> clazz) {
		return (T) map.get(clazz);
	}

}
