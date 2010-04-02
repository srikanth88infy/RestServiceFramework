package com.dominikdorn.rest.services;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RestService {

    public String handleGetById(Class clazz, Object id, String mediaType)
    {
        return "handleGetById(Class clazz, Object id, String mediaType)";
    }

    public String handleGetAll(Class clazz, String mediaType)
    {
        return "    public String handleGetAll(Class clazz, String mediaType)";
    }

    public String handleSearch(Class clazz, Map<String,String> attributes, String mediaType)
    {
        return "    public String handleSearch(Class clazz, Map<String,String> attributes, String mediaType)";
    }

    // create with id
    public String handlePostOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)
    {
        return "    public String handlePostOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)";
    }

    // create without id
    public String handlePostOnResource(Class clazz, String data, String mediaType)
    {
        return "    public String handlePostOnResource(Class clazz, String data, String mediaType)";
    }

    // update with id
    public String handlePutOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)
    {
        return "    public String handlePutOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)";
    }

    // replace collection
    public String handlePutOnResource(Class clazz, String data, String mediaType)
    {
        return "    public String handlePutOnResource(Class clazz, String data, String mediaType)";
    }




    public String handleDeleteOnObject(Class clazz, Object id, String mediaType)
    {
        return "";
    }

    public String handleDeleteOnResource(Class clazz, String mediaType)
    {
        return "";
    }

}
