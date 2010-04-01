package com.dominikdorn.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RemotingError extends Exception {

    /**
     * errorCode. Available ones are:
     * 1 - Marshalling: Marshalling for an unsupported OutputType was requested
     * 2 - Marshalling: Couldn't load the implementation required for serializing the string
     * 3 - Marshalling: An error occurred during the serialization of the given object
     * 4 - Marshalling: An error occurred during the de-serialization to an object
     * 5 - Marshalling: A general, marshalling related error occurred.  
     */
    private int type;

    public RemotingError(int type, String s) {
        super(s);
        this.type = type;
    }
}
