package com.dominikdorn.rest.marshalling;

import com.dominikdorn.rest.services.RemotingError;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface MarshallingStrategy {
    public Object deSerialize(String data, Class clazz) throws RemotingError;
    public String serialize(Object object, Class clazz) throws RemotingError;
}
