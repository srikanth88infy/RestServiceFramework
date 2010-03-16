package com.dominikdorn.tuwien.evs.rest.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * A Rack is a defined space with a name that has a
 * certain amount of space available.
 */
@Entity
public class Rack {
    @Id
    @GeneratedValue
    private Long id;
    @Basic
    private String name;
    @Basic
    private String description;
    @Basic
    private Integer place;

    /** generated methods **/
    public Rack() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
