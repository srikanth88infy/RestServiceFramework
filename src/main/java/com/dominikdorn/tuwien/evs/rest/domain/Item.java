package com.dominikdorn.tuwien.evs.rest.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.GregorianCalendar;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * An Item is a generic name for a Type of Element,
 * e.g. Server Intel Quad Core 3 HE
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    @Basic
    private String name;
    @Basic
    private String description;
    @Basic
    private Integer size;

    /** generated methods **/
    public Item() {
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
     * Size is amount of slots of standardized size
     * @return
     */
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
