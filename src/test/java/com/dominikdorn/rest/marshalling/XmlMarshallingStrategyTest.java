package com.dominikdorn.rest.marshalling;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class XmlMarshallingStrategyTest extends AbstractMarshallingStrategyTest {


    public XmlMarshallingStrategyTest() {
        strategy = new XmlMarshallingStrategy();

        item1_serial_string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><com.dominikdorn.tuwien.evs.rest.domain.Item xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" id=\"i0\" xsi:type=\"com.dominikdorn.tuwien.evs.rest.domain.Item\"><id xsi:type=\"xsd:long\">0</id><name xsi:type=\"xsd:string\">1HE Intel Atom Single-CPU CSE502 Server</name><description xsi:type=\"xsd:string\">1HE Intel Atom Single-CPU CSE502 Server</description><size xsi:type=\"xsd:int\">1</size><placements xsi:type=\"java.util.ArrayList\"/></com.dominikdorn.tuwien.evs.rest.domain.Item>";
        rack1_serial_string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><com.dominikdorn.tuwien.evs.rest.domain.Rack xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" id=\"i0\" xsi:type=\"com.dominikdorn.tuwien.evs.rest.domain.Rack\"><id xsi:type=\"xsd:long\">0</id><name xsi:type=\"xsd:string\">HP Rack 10636 G2 Shock Pallet Rack</name><description xsi:type=\"xsd:string\">Rack 19\" 36U w/d/h 24in/39.8in/68.5in </description><place xsi:type=\"xsd:int\">36</place><placements xsi:type=\"java.util.ArrayList\"/></com.dominikdorn.tuwien.evs.rest.domain.Rack>";
        placement1_serial_string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><com.dominikdorn.tuwien.evs.rest.domain.Placement xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" id=\"i0\" xsi:type=\"com.dominikdorn.tuwien.evs.rest.domain.Placement\"><id xsi:type=\"xsd:long\">0</id><amount xsi:type=\"xsd:int\">5</amount><storingPosition xsi:type=\"xsd:string\">bla</storingPosition><item id=\"i1\" xsi:type=\"com.dominikdorn.tuwien.evs.rest.domain.Item\"><id xsi:type=\"xsd:long\">0</id><name xsi:type=\"xsd:string\">1HE Intel Atom Single-CPU CSE502 Server</name><description xsi:type=\"xsd:string\">1HE Intel Atom Single-CPU CSE502 Server</description><size xsi:type=\"xsd:int\">1</size><placements xsi:type=\"java.util.ArrayList\"/></item><rack id=\"i2\" xsi:type=\"com.dominikdorn.tuwien.evs.rest.domain.Rack\"><id xsi:type=\"xsd:long\">0</id><name xsi:type=\"xsd:string\">HP Rack 10636 G2 Shock Pallet Rack</name><description xsi:type=\"xsd:string\">Rack 19\" 36U w/d/h 24in/39.8in/68.5in </description><place xsi:type=\"xsd:int\">36</place><placements xsi:type=\"java.util.ArrayList\"/></rack></com.dominikdorn.tuwien.evs.rest.domain.Placement>";
    }
}
