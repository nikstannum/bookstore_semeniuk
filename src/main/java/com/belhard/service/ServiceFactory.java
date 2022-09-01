package com.belhard.service;

import java.util.HashMap;
import java.util.Map;

import com.belhard.dao.BookDao;
import com.belhard.dao.DaoFactory;
import com.belhard.dao.UserDao;
import com.belhard.service.impl.BookServiceImpl;
import com.belhard.service.impl.UserServiceImpl;

public class ServiceFactory {
	private final Map<Class<?>, CrudService> map; // TODO: Should me parameterization here?
	public static final ServiceFactory INSTANCE = new ServiceFactory();

	private ServiceFactory() {
		map = new HashMap<>();
		map.put(BookService.class, new BookServiceImpl(DaoFactory.INSTANCE.getDao(BookDao.class)));
		map.put(UserService.class, new UserServiceImpl(DaoFactory.INSTANCE.getDao(UserDao.class)));
	}

	public <T> T getService(Class<T> clazz) {
		return (T) map.get(clazz);
	}

}
