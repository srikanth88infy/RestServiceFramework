package com.dominikdorn.rest.dao;

import javax.persistence.EntityManager;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface GenericJpaDao<TYPE> extends GenericDao<TYPE> {
    public EntityManager getEm() ;
    public GenericJpaDao setEm(EntityManager em) ;

}
