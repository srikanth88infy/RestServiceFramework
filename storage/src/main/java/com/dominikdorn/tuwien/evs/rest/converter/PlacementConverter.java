package com.dominikdorn.tuwien.evs.rest.converter;

import com.dominikdorn.tuwien.evs.rest.annotations.RestConverter;
import com.dominikdorn.tuwien.evs.rest.converter.Converter;
import com.dominikdorn.tuwien.evs.rest.domain.Item;
import com.dominikdorn.tuwien.evs.rest.domain.Placement;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
@RestConverter(forClass = Placement.class)
public class PlacementConverter implements Converter<Placement> {
    @Override
    public Placement getObject(Map data) {
        Placement placement = new Placement();

        if(data.containsKey("id"))
            placement.setId( Long.getLong(data.get("id").toString()));

        if(data.containsKey("item_id"))
        {
            Item i = new Item();
            i.setId(Long.getLong(data.get("item_id").toString()));
            placement.setItem(i);
        }

        if(data.containsKey("rack_id"))
        {
            Rack r = new Rack();
            r.setId( Long.getLong((data.get("rack_id").toString())));
            placement.setRack(r);
        }

        if(data.containsKey("amount"))
        {
            placement.setAmount(Integer.getInteger(data.get("amount").toString()));
        }

        if(data.containsKey("storingPosition"))
        {
            placement.setStoringPosition((String) data.get("storingPosition"));
        }

        return placement;
    }
}
