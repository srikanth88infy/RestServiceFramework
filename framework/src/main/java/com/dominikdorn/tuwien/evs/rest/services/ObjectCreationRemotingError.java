package com.dominikdorn.tuwien.evs.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ObjectCreationRemotingError extends RemotingError {
    public ObjectCreationRemotingError() {
        super(7, "couldn't create object");
    }
}
