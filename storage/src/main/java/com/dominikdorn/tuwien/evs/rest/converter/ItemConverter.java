package com.dominikdorn.tuwien.evs.rest.converter;

import com.dominikdorn.tuwien.evs.rest.annotations.RestConverter;
import com.dominikdorn.tuwien.evs.rest.converter.Converter;
import com.dominikdorn.tuwien.evs.rest.domain.Item;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
@RestConverter(forClass = Item.class)
public class ItemConverter implements Converter<Item> {
    @Override
    public Item getObject(Map data) {
        Item o = new Item();
        if(data.containsKey("id"))
        {
            o.setId(Long.getLong(data.get("id").toString()));
        }

        if(data.containsKey("name"))
        {
            o.setName(data.get("name").toString());
        }

        if(data.containsKey("description"))
        {
            o.setDescription(data.get("description").toString());
        }

        if(data.containsKey("size"))
        {
            o.setSize(Integer.getInteger(data.get("size").toString()));
        }

        return o;
    }
}
