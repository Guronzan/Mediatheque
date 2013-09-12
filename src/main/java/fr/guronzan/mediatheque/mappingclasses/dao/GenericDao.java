package fr.guronzan.mediatheque.mappingclasses.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
    K create(final T persistentObject);

    T get(final K id);

    List<T> getAll();

    void update(final T persistentObject);

    void saveOrUpdate(final T persistentObject);

    void delete(final T persistentObject);

}