package com.dominikdorn.tuwien.evs.rest.converter;

import java.util.Map;

import com.dominikdorn.rest.annotations.RestConverter;
import com.dominikdorn.rest.converter.Converter;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
@RestConverter(forClass = Rack.class)
public class RackConverter implements Converter<Rack> {
    @Override
    public Rack getObject(Map data) {
        Rack o = new Rack();
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

        if(data.containsKey("place"))
        {
            o.setPlace(Integer.getInteger(data.get("place").toString()));
        }
        
        return o;
    }
}
