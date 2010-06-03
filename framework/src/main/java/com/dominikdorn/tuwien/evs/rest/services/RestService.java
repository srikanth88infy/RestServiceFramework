package com.dominikdorn.tuwien.evs.rest.services;

import java.util.List;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface RestService {
    /**
     * Returns a specific instance given by an Id.
     * @param clazz
     * @param id
     * @return
     * @throws RemotingError
     */
    Object handleGetById(Class clazz, Object id) throws RemotingError;

    /**
     * Returns all instances of an resource.
     * @param clazz
     * @return
     * @throws RemotingError
     */
    List handleGetAll(Class clazz) throws RemotingError;

    /**
     * Searches for instances of a resource with the given attributes
     * @param clazz
     * @param attributes
     * @return
     * @throws RemotingError
     */
    List handleSearch(Class clazz, Map<String, String> attributes) throws RemotingError;

    /**
     * Updates a given instance of a resource.
     * @param clazz
     * @param id
     * @param o
     * @return
     * @throws RemotingError
     */
    Object handlePostOnObject(Class clazz, Object id, Object o) throws RemotingError;

    /**
     * Creates a new instance for the given resource and persists it.
     * @param clazz
     * @param o
     * @return
     * @throws RemotingError
     */
    Object handlePostOnResource(Class clazz, Object o) throws RemotingError;

    /**
     * Persists an instance of a resource with a specific Id. If an instance with this
     * id already exists, update the instance.
     * @param clazz
     * @param id
     * @param o
     * @return
     * @throws RemotingError
     */
    Object handlePutOnObject(Class clazz, Object id, Object o) throws RemotingError;

    /**
     * Updates a whole resource.
     * @param clazz
     * @param data
     * @return
     * @throws RemotingError
     */
    String handlePutOnResource(Class clazz, String data) throws RemotingError;

    /**
     * Deletes an Instance of a certain resource.
     * Returns true on success, false otherwise
     * @param clazz
     * @param id
     * @return
     * @throws RemotingError
     */
    void handleDeleteOnObject(Class clazz, Object id) throws RemotingError;


    /**
     * Deletes a whole resource, meaning emptying a table.
     * Returns the number of objects deleted.
     * @param clazz
     * @return
     * @throws RemotingError
     */
    int handleDeleteOnResource(Class clazz) throws RemotingError;
}
