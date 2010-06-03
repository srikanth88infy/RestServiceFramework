package com.dominikdorn.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class AlreadyExistsRemotingError extends RemotingError {
    public AlreadyExistsRemotingError() {
        super(9, "Post on already existing Object not allowed");
    }
}
