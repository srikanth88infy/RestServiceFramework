package com.dominikdorn.tuwien.evs.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RemotingError extends RuntimeException {

    /**
     * errorCode. Available ones are:
     *  1 - Marshalling: Marshalling for an unsupported OutputType was requested
     *  2 - Marshalling: Couldn't load the implementation required for serializing the string
     *  3 - Marshalling: An error occurred during the serialization of the given object
     *  4 - Marshalling: An error occurred during the de-serialization to an object
     *  5 - Marshalling: A general, marshalling related error occurred.
     *  6 - DAO: Object not found
     *  7 - Generic: Couldn't create Object
     *  8 - Generic: Couldn't access object property
     *  9 - Post on already existing Object not allowed
     * 10 - Unsupported Operation Error
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return super.getMessage(); 
    }

    public RemotingError(int type, String s) {
        super(s);
        this.type = type;
    }
}
