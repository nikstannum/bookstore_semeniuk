package com.belhard.service.factory;

import java.util.HashMap;
import java.util.Map;

import com.belhard.dao.BookDao;
import com.belhard.dao.OrderDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.factory.DaoFactory;
import com.belhard.service.BookService;
import com.belhard.service.CrudService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.impl.BookServiceImpl;
import com.belhard.service.impl.OrderServiceImpl;
import com.belhard.service.impl.UserServiceImpl;

public class ServiceFactory {
	private final Map<Class<?>, CrudService<?, ?>> map;
	public static final ServiceFactory INSTANCE = new ServiceFactory();

	private ServiceFactory() {
		map = new HashMap<>();
		map.put(BookService.class, new BookServiceImpl(DaoFactory.INSTANCE.getDao(BookDao.class)));
		map.put(UserService.class, new UserServiceImpl(DaoFactory.INSTANCE.getDao(UserDao.class)));
		map.put(OrderService.class, new OrderServiceImpl(DaoFactory.INSTANCE.getDao(OrderDao.class), DaoFactory.INSTANCE.getDao(BookDao.class)));
	}

	public <T> T getService(Class<T> clazz) {
		return (T) map.get(clazz);
	}

}
