package com.dominikdorn.rest.marshalling;

import com.dominikdorn.rest.services.RemotingError;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public abstract class MarshallingStrategy {
    public abstract Object deSerialize(String data, Class clazz) throws RemotingError;
    public abstract String serialize(Object object, Class clazz) throws RemotingError;
}
