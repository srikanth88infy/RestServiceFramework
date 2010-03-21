package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JpaItemDao extends AbstractJpaDao<Item> implements ItemDao {

}
