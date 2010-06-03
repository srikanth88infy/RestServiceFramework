package com.dominikdorn.rest.invoking;

import com.dominikdorn.rest.services.OutputType;
import com.dominikdorn.rest.services.RemotingError;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface Invoker {

    String handleGetById(Class clazz, Object id, OutputType mediaType) throws RemotingError;

    String handleGetAll(Class clazz, OutputType mediaType) throws RemotingError;

    String handleSearch(Class clazz, Map<String, String> attributes, OutputType mediaType) throws RemotingError;

    String handlePostOnObject(Class clazz, Object id, Map<String, String> attributes, OutputType mediaType) throws RemotingError;

    String handlePostOnResource(Class clazz, Map<String, String> data, OutputType mediaType) throws RemotingError;

    String handlePutOnObject(Class clazz, Object id, Map<String, String> attributes, OutputType mediaType) throws RemotingError;

    String handlePutOnResource(Class clazz, String data, OutputType mediaType) throws RemotingError;

    void handleDeleteOnObject(Class clazz, Object id, OutputType mediaType) throws RemotingError;

    String handleDeleteOnResource(Class clazz, OutputType mediaType) throws RemotingError;
}
