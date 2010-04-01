package com.dominikdorn.rest.services;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ObjectRegistry {

    private Map<String, Class> objectDatabase;

    public boolean isRegisteredItemName(String name){
        return objectDatabase.containsKey(name);
    }

    public void setObjectDatabase(Map<String, Class> objectDatabase) {
        this.objectDatabase = objectDatabase;
    }
}
