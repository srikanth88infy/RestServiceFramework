package com.dominikdorn.tuwien.evs.rest.domain;

import com.dominikdorn.tuwien.evs.rest.annotations.Restful;

import javax.persistence.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PLACEMENT_GEN")
    @SequenceGenerator(name="PLACEMENT_GEN", allocationSize=25, sequenceName = "placement_seq")
    long id;   

    @Basic
    private Integer amount;
    @Basic
    private String storing_position;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")
    private Item item;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "RACK_ID", referencedColumnName = "ID")
    private Rack rack;

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

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }
}
