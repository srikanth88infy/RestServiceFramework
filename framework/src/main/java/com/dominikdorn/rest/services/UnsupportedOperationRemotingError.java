package com.dominikdorn.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class UnsupportedOperationRemotingError extends RemotingError {
    public UnsupportedOperationRemotingError() {
        super(10, "Unsupported Operation Error");
    }
}
