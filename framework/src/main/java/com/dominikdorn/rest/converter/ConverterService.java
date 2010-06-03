package com.dominikdorn.rest.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ConverterService {

    Map<Class, Converter> converters = new HashMap<Class, Converter>();
    

    public void registerConverter(Class clazz, Converter converter)
    {
        System.out.println("registering converter for class " + clazz.getName());
        converters.put(clazz, converter);
    }

    public Converter getConverter(Class clazz)
    {
        return converters.get(clazz);
    }

}
