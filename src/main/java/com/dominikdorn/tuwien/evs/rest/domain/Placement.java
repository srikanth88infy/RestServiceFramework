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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Placement placement = (Placement) o;

        if (amount != placement.amount) return false;
        if (id != placement.id) return false;
        if (item != null ? !item.equals(placement.item) : placement.item != null) return false;
        if (rack != null ? !rack.equals(placement.rack) : placement.rack != null) return false;
        if (storingPosition != null ? !storingPosition.equals(placement.storingPosition) : placement.storingPosition != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + amount;
        result = 31 * result + (storingPosition != null ? storingPosition.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (rack != null ? rack.hashCode() : 0);
        return result;
    }
}
