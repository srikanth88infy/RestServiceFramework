package com.dominikdorn.tuwien.evs.rest.dao;

import com.dominikdorn.tuwien.evs.rest.domain.Placement;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface PlacementDao {
    public Placement persist(Placement placement);

    public Placement update(Placement placement);

    public List<Placement> getAll();

    public Placement getById(Long id);

    public void delete(Placement placement);
}
