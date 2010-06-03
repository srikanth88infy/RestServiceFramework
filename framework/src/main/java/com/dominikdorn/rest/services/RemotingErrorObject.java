package com.dominikdorn.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RemotingErrorObject {
    private int type;
    private String message;

    public RemotingErrorObject(RemotingError error)
    {
        this.type = error.getType();
        this.message = error.getMessage();
    }

    public RemotingErrorObject(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
