package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Rack;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * DAO Interface for the Rack Data Access Object
 */
public interface RackDao {
    public Rack persist(Rack rack);

    public Rack update(Rack rack);

    public List<Rack> getAll();

    public Rack getById(Long id);

    public void delete(Rack rack);

}
