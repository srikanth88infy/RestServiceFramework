package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Item;
import org.junit.Test;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public abstract class AbstractItemDaoTest {

    protected ItemDao dao;

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

    final Item item3 = new Item(
            200l,
            "1HE Intel Single-CPU SC813M Server (Nehalem) ",
            "1HE Intel Single-CPU SC813M Server (Nehalem) ",
            1
    );

    final Item item4 = new Item(
            "1HE Intel Atom D510 Single-CPU CSE502 Server ",
            "1HE Intel Atom D510 Single-CPU CSE502 Server", 1);

    @Test(expected = PersistenceException.class)
    public void testPersist_shouldThrowExceptionWhenNullParameter()
    {
        Item item = null;
        dao.persist(item);
    }

    @Test
    public void testPersist_shouldWork()
    {
        Item newItem = dao.persist(item1);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);
    }

    @Test(expected = PersistenceException.class)
    public void testGetById_nullId()
    {
        dao.getById(null);
    }

    @Test(expected = PersistenceException.class)
    public void testGetById_negativeId_shouldThrowException()
    {
        dao.getById(-1l);
    }

    @Test(expected = PersistenceException.class)
    public void testGetById_zeroId_shouldThrowException()
    {
        dao.getById(0l);
    }

    @Test
    public void testGetById_shouldWorkWithPrePersistedEntity()
    {
        Item newItem = dao.persist(item2);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);

        Item resultItem = dao.getById(newItem.getId());
        assertNotNull(resultItem);
        assertEquals(newItem.getId(), resultItem.getId());
        assertEquals(newItem.getDescription(), resultItem.getDescription());
        assertEquals(newItem.getName(), resultItem.getName());
        assertEquals(newItem.getSize(), resultItem.getSize());
    }


    @Test
    public void testGetById_shouldFailWithNonExistingEntity()
    {
        // no persist here!
        Item resultItem = dao.getById(item3.getId());
        assertNull(resultItem);
    }

    @Test(expected = PersistenceException.class)
    public void testUpdate_shouldThrowExceptionWhenNullParameter()
    {
        dao.persist(null);
    }

    @Test
    public void testUpdate_shouldWork()
    {
        Item newItem = dao.persist(item4);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);

        newItem.setSize(2);

        Item newItem2 = dao.update(newItem);
        assertNotNull(newItem2);
        assertEquals(2, (int) newItem2.getSize());
    }

    @Test
    public void testGetAll_shouldReturnNothing()
    {
        List<Item> results = dao.getAll();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testGetAll_shouldFindItems()
    {
        dao.persist(item1);
        dao.persist(item2);
        dao.persist(item3);
        List<Item> results = dao.getAll();
        assertNotNull(results);
        assertEquals(3,results.size());
    }

    @Test(expected = PersistenceException.class)
    public void testDelete_nullParam_shouldThrowException()
    {
        dao.delete(null);
        fail();
    }

    @Test
    public void testDelete_shouldWorkCorrectly()
    {
        List<Item> items = dao.getAll();
        assertTrue(items.isEmpty());

        dao.persist(item1);

        items = dao.getAll();
        assertEquals(1, items.size());

        dao.delete(item1);

        items = dao.getAll();
        assertTrue(items.isEmpty());
    }


}
