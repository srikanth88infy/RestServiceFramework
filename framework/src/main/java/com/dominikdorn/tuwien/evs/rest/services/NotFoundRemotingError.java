package com.dominikdorn.tuwien.evs.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class NotFoundRemotingError extends RemotingError {
    public NotFoundRemotingError(String s) {
        super(6, s);
    }

}
