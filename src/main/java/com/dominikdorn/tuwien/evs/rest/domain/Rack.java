package com.dominikdorn.tuwien.evs.rest.domain;

import com.dominikdorn.tuwien.evs.rest.annotations.Restful;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * A Rack is a defined space with a name that has a
 * certain amount of space available.
 */
@Entity
@Restful("racks")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RACK_GEN")
    @SequenceGenerator(name="RACK_GEN", allocationSize=25, sequenceName = "rack_seq")
    private long id;
    @Basic
    private String name;
    @Basic
    private String description;
    @Basic
    private int place;

    @OneToMany(mappedBy = "rack")
    private List<Placement> placements = new ArrayList<Placement>();

    /** generated methods **/
    public Rack() {
    }

    public Rack(long id, String name, String description, Integer place) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
    }

    public Rack(String name, String description, Integer place) {
        this.name = name;
        this.description = description;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * place is amount of slots of standardized size that are
     * available in the rack
     * @return
     */
    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public List<Placement> getPlacements() {
        return placements;
    }

    public void setPlacements(List<Placement> placements) {
        this.placements = placements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rack rack = (Rack) o;

        if (id != rack.id) return false;
        if (place != rack.place) return false;
        if (description != null ? !description.equals(rack.description) : rack.description != null) return false;
        if (name != null ? !name.equals(rack.name) : rack.name != null) return false;
        if (placements != null ? !placements.equals(rack.placements) : rack.placements != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + place;
        result = 31 * result + (placements != null ? placements.hashCode() : 0);
        return result;
    }
}
