package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Rack;
import org.junit.Test;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public abstract class AbstractRackDaoTest {

    protected RackDao dao;

    final Rack item1 = new Rack(
            "HP Rack 10636 G2 Shock Pallet Rack",
            "Rack 19\" 36U w/d/h 24in/39.8in/68.5in ",
            36
    );
    final Rack item2 = new Rack(
            "Belkin Premium Rack ",
            "Rack 19\" 42U w/d/h 24in/42.1in/79.9in ",
            42
    );

    final Rack item3 = new Rack(
            200l,
            "HP Rack 10842 G2 Wide Rack Cabinet Shock Pallet Rack",
            "Rack 19\" 42U w/d/h 31.5in/39.8in/78.7in ",
            42
    );
    final Rack item4 = new Rack(
            "APC NetShelter SX Enclosure with Sides Rack",
            "Rack 19\" 42U w/d/h 23.6in/42.1in/78.3in ",
            42
    );


    @Test(expected = PersistenceException.class)
    public void testPersist_shouldThrowExceptionWhenNullParameter()
    {
        Rack item = null;
        dao.persist(item);
    }

    @Test
    public void testPersist_shouldWork()
    {
        Rack newItem = dao.persist(item1);

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
        Rack newItem = dao.persist(item2);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);

        Rack resultItem = dao.getById(newItem.getId());
        assertNotNull(resultItem);
        assertEquals(newItem.getId(), resultItem.getId());
        assertEquals(newItem.getDescription(), resultItem.getDescription());
        assertEquals(newItem.getName(), resultItem.getName());
        assertEquals(newItem.getPlace(), resultItem.getPlace());
    }


    @Test
    public void testGetById_shouldFailWithNonExistingEntity()
    {
        // no persist here!
        Rack resultItem = dao.getById(item3.getId());
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
        Rack newItem = dao.persist(item4);

        assertNotNull(newItem);
        assertTrue(newItem.getId() > 0);

        newItem.setPlace(2);

        Rack newItem2 = dao.update(newItem);
        assertNotNull(newItem2);
        assertEquals(2, (int) newItem2.getPlace());
    }

    @Test
    public void testGetAll_shouldReturnNothing()
    {
        List<Rack> results = dao.getAll();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testGetAll_shouldFindItems()
    {
        dao.persist(item1);
        dao.persist(item2);
        dao.persist(item3);
        List<Rack> results = dao.getAll();
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
        List<Rack> items = dao.getAll();
        assertTrue(items.isEmpty());

        dao.persist(item1);

        items = dao.getAll();
        assertEquals(1, items.size());

        dao.delete(item1);

        items = dao.getAll();
        assertTrue(items.isEmpty());
    }


}
