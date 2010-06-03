package com.dominikdorn.tuwien.evs.rest.marshalling;

import com.dominikdorn.rest.marshalling.MarshallingStrategy;
import com.dominikdorn.tuwien.evs.rest.domain.Item;
import com.dominikdorn.tuwien.evs.rest.domain.Placement;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public abstract class AbstractMarshallingStrategyTest {

    MarshallingStrategy strategy;

    final Item item_item1 = new Item(
            "1HE Intel Atom Single-CPU CSE502 Server",
            "1HE Intel Atom Single-CPU CSE502 Server",
            1
    );

    final Rack rack_item1 = new Rack(
            "HP Rack 10636 G2 Shock Pallet Rack",
            "Rack 19\" 36U w/d/h 24in/39.8in/68.5in ",
            36
    );

    final Placement placement_item1 = new Placement(item_item1, rack_item1, 5, "bla");


    String item1_serial_string;
    String rack1_serial_string;
    String placement1_serial_string;


    @Test
    public void testDeSerialize_item() throws Exception {
        assertEquals(item_item1, strategy.deSerialize(item1_serial_string, Item.class));
    }

    @Test
    public void testDeSerialize_rack() throws Exception {
        assertEquals(rack_item1, strategy.deSerialize(rack1_serial_string, Rack.class));
    }

    @Test
    public void testDeSerialize_placement() throws Exception {
        assertEquals(placement_item1, strategy.deSerialize(placement1_serial_string, Placement.class));
    }

    @Test
    public void testSerialize_item() throws Exception {
        assertEquals(item1_serial_string, strategy.serialize(item_item1, Item.class));
    }

    @Test
    public void testSerialize_rack() throws Exception {
        assertEquals(rack1_serial_string, strategy.serialize(rack_item1, Rack.class));
    }

    @Test
    public void testSerialize_placement() throws Exception {
        assertEquals(placement1_serial_string, strategy.serialize(placement_item1, Placement.class));
    }
}
