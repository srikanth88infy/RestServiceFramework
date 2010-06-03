package com.dominikdorn.rest.converter;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface Converter<T> {

    public T getObject(Map data);
}
