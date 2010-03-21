package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Item;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * DAO Interface for the Item Data Access Object
 */
public interface ItemDao {

    public Item persist(Item item);

    public Item update(Item item);

    public List<Item> getAll();

    public Item getById(Long id);

    public void delete(Item item);

}
