package com.busqueumlugar.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T,Type extends Serializable> {
	
	void beginTransaction();
	void commitTransaction();
	void save(T entity);
	void update(T entity);
	void delete (T entity);
	void delete(Class<T> persistentClass, Long id);
	List<T> listAll();
	
}
