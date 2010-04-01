package com.dominikdorn.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class AbstractJpaDao<TYPE> {
    @PersistenceContext
    protected EntityManager em;

    protected Class entityClass;

    public AbstractJpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<TYPE>) genericSuperclass.getActualTypeArguments()[0];
    }

    public EntityManager getEm() {
        return em;
    }

    public AbstractJpaDao setEm(EntityManager em) {
        this.em = em;
        return this;
    }


    public TYPE persist(TYPE item) {
        if (item == null)
            throw new PersistenceException("Item may not be null");
        em.persist(item);
        return item;
    }

    public TYPE update(TYPE item) {
        if (item == null)
            throw new PersistenceException("Item may not be null");

        em.merge(item);
        return item;
    }

    public List<TYPE> getAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();       
    }

    public TYPE getById(Long id) {
        if (id == null || id < 1)
            throw new PersistenceException("Id may not be null or negative");

        return (TYPE) em.find(entityClass, id);
    }

    public void delete(TYPE item) {
        if (item == null)
            throw new PersistenceException("Item may not be null");

        em.remove(em.merge(item));
    }
}
