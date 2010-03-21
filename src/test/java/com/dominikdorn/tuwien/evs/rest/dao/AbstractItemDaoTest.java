package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Item;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public abstract class AbstractItemDaoTest {

    protected ItemDao dao;

    @Test(expected = PersistenceException.class)
    public void testPersist_shouldThrowExceptionWhenNullParameter()
    {
        Item item = null;
        dao.persist(item);
    }

    @Test
    public void testPersist_shouldWork()
    {
        Item item = new Item();
        item.setName("1HE Intel Atom Single-CPU CSE502 Server");
        item.setSize(1);
        item.setDescription("1HE Intel Atom Single-CPU CSE502 Server ");

        Item newItem = dao.persist(item);

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
        Item item = new Item();
        item.setName("1HE Intel Single-CPU SC811 Server (Nehalem) ");
        item.setSize(1);
        item.setDescription("1HE Intel Single-CPU SC811 Server (Nehalem) ");

        Item newItem = dao.persist(item);

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
        Item item = new Item();
        item.setId(200l);
        item.setName("1HE Intel Single-CPU SC811 Server (Nehalem) ");
        item.setSize(1);
        item.setDescription("1HE Intel Single-CPU SC811 Server (Nehalem) ");

        // no persist here!

        Item resultItem = dao.getById(item.getId());
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
        Item item = new Item();
        item.setName("1HE Intel Atom D510 Single-CPU CSE502 Server ");
        item.setSize(1);
        item.setDescription("1HE Intel Atom D510 Single-CPU CSE502 Server");

        Item newItem = dao.persist(item);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);

        newItem.setSize(2);

        Item newItem2 = dao.update(newItem);
        assertNotNull(newItem2);
        assertEquals(2, (int) newItem2.getSize());
    }




}
