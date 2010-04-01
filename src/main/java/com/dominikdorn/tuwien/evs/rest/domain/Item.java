package com.dominikdorn.tuwien.evs.rest.domain;

import com.dominikdorn.tuwien.evs.rest.annotations.Restful;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 * <p/>
 * An Item is a generic name for a Type of Element,
 * e.g. Server Intel Quad Core 3 HE
 */
@Entity
@Restful("items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ITEM_GEN")
    @SequenceGenerator(name="ITEM_GEN", allocationSize=25, sequenceName = "item_seq")
    private long id;
    @Basic
    private String name;
    @Basic
    private String description;
    @Basic
    private Integer size;

    @OneToMany(mappedBy = "item")
    private List<Placement> placements = new ArrayList<Placement>();

    /**
     * generated methods *
     */
    public Item() {
    }

    public Item(Long id, String name, String description, Integer size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
    }

    public Item(String name, String description, Integer size) {
        this.name = name;
        this.description = description;
        this.size = size;
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
     *
     * @return
     */
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Placement> getPlacements() {
        return placements;
    }

    public void setPlacements(List<Placement> placements) {
        this.placements = placements;
    }
}
