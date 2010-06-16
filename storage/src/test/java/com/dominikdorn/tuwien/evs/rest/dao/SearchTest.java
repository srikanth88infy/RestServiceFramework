package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.rest.dao.AbstractJpaDao;
import com.dominikdorn.tuwien.evs.rest.domain.Item;
import org.eclipse.persistence.internal.oxm.schema.model.Restriction;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class SearchTest{
    AbstractJpaDao dao;
    private static EntityManagerFactory emf;

    private EntityManager em;


    final Item item1 = new Item(
            "1HE Intel Atom Single-CPU CSE502 Server",
            "1HE Intel Atom Single-CPU CSE502 Server",
            1
    );

    final Item item2 = new Item(
            "1HE Intel Single-CPU SC811 Server (Nehalem) ",
            "1HE Intel Single-CPU SC811 Server (Nehalem) ",
            1
    );



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

        dao = new AbstractJpaDao<Item>(){};
        dao.setEm(em);
        em.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {

        if (em.getTransaction().isActive())
            em.getTransaction().rollback();

        if (em.isOpen())
            em.close();
    }

    public SearchTest() {

    }


    @Test
    public void testSearch_expectEmptyResult()
    {
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("name", "irgendwas");
        List<Item> results = findByAttributes(attributes);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearch_shouldReturnAll()
    {
        dao.persist(item1);
        dao.persist(item2);

        Map<String,String> attributes = new HashMap<String,String>();
//        attributes.put("name", "irgendwas");
        List<Item> results = findByAttributes(attributes);
        assertNotNull(results);
        assertEquals(2, results.size());
        assertFalse(results.isEmpty());
    }

    @Test
    public void testSearch_shouldReturnOnlyOne()
    {
        dao.persist(item1);
        dao.persist(item2);

        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("name", "SC811");
        List<Item> results = findByAttributes(attributes);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertFalse(results.isEmpty());
    }


    public List<Item> findByAttributes(Map<String, String> attributes) {
        return dao.findByAttributes(attributes);
    }

}
