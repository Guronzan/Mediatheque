package fr.guronzan.mediatheque.mappingclasses.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
	K create(T persistentObject);

	T get(K id);

	List<T> getAll();

	void update(T persistentObject);

	void createOrUpdate(T persistentObject);

	void delete(T persistentObject);

}