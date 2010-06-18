package com.dominikdorn.tuwien.evs.rest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.dominikdorn.rest.annotations.Restful;
import com.dominikdorn.rest.annotations.Searchable;

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
    @Searchable
    private String name;

    @Basic
    @Searchable
    private String description;

    @Basic
    private Integer size;

    @OneToMany(mappedBy = "item")
    private List<Placement> placements;

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
        placements = new ArrayList<Placement>();
    }

    public Item(String name, String description, Integer size) {
        this.name = name;
        this.description = description;
        this.size = size;
        placements = new ArrayList<Placement>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
       // if (placements != null ? !placements.equals(item.placements) : item.placements != null) return false;
        if (size != null ? !size.equals(item.size) : item.size != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        //result = 31 * result + (placements != null ? placements.hashCode() : 0);
        return result;
    }
}
