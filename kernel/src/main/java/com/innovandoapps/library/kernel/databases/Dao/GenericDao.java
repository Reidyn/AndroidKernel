package com.innovandoapps.library.kernel.databases.Dao;

import java.util.List;

public interface GenericDao<T> {

    Long insert(T obj);

    Integer update(T obj);

    T find(Long id);

    List<T> getList();

    T delete(T obj);
}
