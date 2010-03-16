package com.dominikdorn.tuwien.evs.rest.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * A placement maps a certain item (with a given quantity)
 * to a certain Rack. One has to
 */
@Entity
public class Placement {

    @Basic
    private Integer amount;
    @Basic
    private String storing_position;

    @OneToMany
    private Item item;

    @OneToMany
    private Placement placement;

    /** Generated code **/

    public Placement() {
    }

    /**
     * Returns the Amount of Items this Placement contains.
     *
     * The constraint placement.amount * item.size <= rack.place
     * @return
     */
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStoring_position() {
        return storing_position;
    }

    public void setStoring_position(String storing_position) {
        this.storing_position = storing_position;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }
}
