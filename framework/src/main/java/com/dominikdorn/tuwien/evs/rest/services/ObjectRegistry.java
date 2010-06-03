package com.dominikdorn.tuwien.evs.rest.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ObjectRegistry {

    private Map<String, Class> objectDatabase;

    private Map<Class, Set<String>> searchableFields = new HashMap<Class, Set<String>>();


    public void addSearchableField(Class clazz, String field) {
        if (!searchableFields.containsKey(clazz))
            searchableFields.put(clazz, new HashSet<String>());

        searchableFields.get(clazz).add(field);
    }

    public boolean isRegisteredItemName(String name) {
        return objectDatabase.containsKey(name);
    }

    public Class getRegisteredClass(String itemName) {
        return objectDatabase.get(itemName);
    }


    public void setObjectDatabase(Map<String, Class> objectDatabase) {
        this.objectDatabase = objectDatabase;
    }

    public Set<String> getSearchableFields(Class clazz) {
        return searchableFields.get(clazz);
    }
}
