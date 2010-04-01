package com.dominikdorn.rest.marshalling;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JsonMarshallingStrategyTest extends AbstractMarshallingStrategyTest {

    public JsonMarshallingStrategyTest() {
        strategy = new JsonMarshallingStrategy();
        item1_serial_string = "{\"id\":0,\"name\":\"1HE Intel Atom Single-CPU CSE502 Server\",\"description\":\"1HE Intel Atom Single-CPU CSE502 Server\",\"size\":1,\"placements\":[]}";
        rack1_serial_string = "{\"id\":0,\"name\":\"HP Rack 10636 G2 Shock Pallet Rack\",\"description\":\"Rack 19\\\" 36U w/d/h 24in/39.8in/68.5in \",\"place\":36,\"placements\":[]}";
        placement1_serial_string = "{\"id\":0,\"amount\":5,\"storingPosition\":\"bla\",\"item\":{\"id\":0,\"name\":\"1HE Intel Atom Single-CPU CSE502 Server\",\"description\":\"1HE Intel Atom Single-CPU CSE502 Server\",\"size\":1,\"placements\":[]},\"rack\":{\"id\":0,\"name\":\"HP Rack 10636 G2 Shock Pallet Rack\",\"description\":\"Rack 19\\\" 36U w/d/h 24in/39.8in/68.5in \",\"place\":36,\"placements\":[]}}";

    }


}
