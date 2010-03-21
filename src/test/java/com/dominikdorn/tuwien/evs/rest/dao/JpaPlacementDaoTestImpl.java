package com.dominikdorn.tuwien.evs.rest.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JpaPlacementDaoTestImpl extends AbstractPlacementDaoTest {

    private static EntityManagerFactory emf;

    private EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("RestPersistenceUnit");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }

    @Before
    public void beginTransaction() {
        em = emf.createEntityManager();
        JpaPlacementDao realDao = new JpaPlacementDao();
        realDao.setEm(em);
        dao = realDao;
        itemDao = (ItemDao) new JpaItemDao().setEm(em);
        rackDao = (RackDao) new JpaRackDao().setEm(em);
        
        em.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {

        if (em.getTransaction().isActive())
            em.getTransaction().rollback();

        if (em.isOpen())
            em.close();
    }

    public JpaPlacementDaoTestImpl() {

    }

}
