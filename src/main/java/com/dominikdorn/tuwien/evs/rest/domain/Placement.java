package com.dominikdorn.tuwien.evs.rest.domain;

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
    private int amount;
    @Basic
    private String storingPosition;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")
    private Item item;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "RACK_ID", referencedColumnName = "ID")
    private Rack rack;

    /** Generated code **/

    public Placement() {
    }

    public Placement(Item item, Rack rack, Integer amount, String storing_position) {
        this.item = item;
        this.rack = rack;
        this.amount = amount;
        this.storingPosition = storing_position;
    }

    public Placement(long id, Item item, Rack rack, Integer amount, String storing_position) {
        this.id = id;
        this.item = item;
        this.rack = rack;
        this.amount = amount;
        this.storingPosition = storing_position;
    }

    /**
     * Returns the Amount of Items this Placement contains.
     *
     * The constraint placement.amount * item.size <= rack.place
     * @return
     */
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStoringPosition() {
        return storingPosition;
    }

    public void setStoringPosition(String storingPosition) {
        this.storingPosition = storingPosition;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
