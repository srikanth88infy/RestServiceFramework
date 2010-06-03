package com.dominikdorn.tuwien.evs.rest.dao;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class TransactionalJpaDao implements GenericDao<Object>{

    private EntityManager em;

    private GenericJpaDao jpaDao;

    public TransactionalJpaDao(EntityManager em, GenericJpaDao jpaDao) {
        this.em = em;
        this.jpaDao = jpaDao;
        jpaDao.setEm(em);
    }

    @Override
    public Object persist(Object item) {
        em.getTransaction().begin();
        Object result = jpaDao.persist(item);
        em.getTransaction().commit();
        return result;
    }

    @Override
    public Object update(Object item) {
        em.getTransaction().begin();
        Object result = jpaDao.update(item);
        em.getTransaction().commit();
        return result;
    }

    @Override
    public List getAll() {
// We dont need a transaction here
        return jpaDao.getAll();
//        em.getTransaction().begin();
//        Object result = jpaDao.persist(item);
//        em.getTransaction().commit();
//        return result;

    }

    @Override
    public Object getById(Long id) {
        return jpaDao.getById(id);
    }

    @Override
    public void delete(Object item) {
        em.getTransaction().begin();
        jpaDao.delete(item);
        em.getTransaction().commit();
    }

    @Override
    public List<Object> findByAttributes(Map<String, String> attributes) {
        return jpaDao.findByAttributes(attributes);
    }
}
