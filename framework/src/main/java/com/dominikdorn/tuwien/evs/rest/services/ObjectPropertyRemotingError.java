package com.dominikdorn.tuwien.evs.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ObjectPropertyRemotingError extends RemotingError {
    public ObjectPropertyRemotingError()
    {
        super(8, "couldn't access a property of the given object");
    }
}
