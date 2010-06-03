package com.dominikdorn.tuwien.evs.rest.dao;

import java.util.List;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface GenericDao<TYPE> {
    TYPE persist(TYPE item);

    TYPE update(TYPE item);

    List<TYPE> getAll();

    TYPE getById(Long id);

    void delete(TYPE item);

    List<TYPE> findByAttributes(Map<String, String> attributes);
}
