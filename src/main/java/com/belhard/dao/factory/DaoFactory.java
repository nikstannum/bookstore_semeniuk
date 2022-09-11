package com.belhard.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.belhard.dao.BookDao;
import com.belhard.dao.CrudDao;
import com.belhard.dao.OrderDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.dao.impl.OrderDaoImpl;
import com.belhard.dao.impl.OrderInfoDaoImpl;
import com.belhard.dao.impl.UserDaoImpl;
import com.belhard.service.BookService;

public class DaoFactory {
	public static final DaoFactory INSTANCE = new DaoFactory();
	private final Map<Class<?>, CrudDao<?, ?>> map;

	private DaoFactory() {
		DataSource dataSource = DataSource.INSTANCE;
		map = new HashMap<>();
		map.put(BookDao.class, new BookDaoImpl(dataSource));
		map.put(UserDao.class, new UserDaoImpl(dataSource));
		map.put(OrderInfoDao.class, new OrderInfoDaoImpl(dataSource, getDao(BookDao.class)));
		map.put(OrderDao.class, new OrderDaoImpl(dataSource, getDao(OrderInfoDao.class), getDao(UserDao.class)));
	}

	public <T> T getDao(Class<T> clazz) {
		return (T) map.get(clazz);
	}

}
