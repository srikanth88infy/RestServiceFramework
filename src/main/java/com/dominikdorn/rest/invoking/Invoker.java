package com.dominikdorn.rest.invoking;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.services.OutputType;
import com.dominikdorn.rest.services.RemotingError;
import com.dominikdorn.rest.services.RestService;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * This class is the Invoker. It takes the data given from the RequestHandler,
 * matches the given MediaType (String) to a specific OutputType, invokes the
 * marshaller for Object translation and then invokes the specific service method.
 *
 *
 * * Invoker(s) accept(s) invocations from requestors
 * * Invoker reads the request and demarshalls it
 * * Looks up the correct local object & operation and
 * * Performs the invocation on the targeted remote object
 */
public class Invoker {

    private Marshaller marshaller;

    private RestService service;

    public Invoker(Marshaller marshaller, RestService service) {
        this.marshaller = marshaller;
        this.service = service;
    }

    public String handleGet(String pathInfo, String mediaType)
    {
        return "    public String handleGet(String pathInfo, String mediaType)";
    }

    public String handlePut(String pathInfo, Map<String,String> params, String mediaType)
    {
        return "    public String handlePut(String pathInfo, Map<String,String> params, String mediaType)";
    }

    public String handlePost(String pathInfo, Map<String,String> params, String mediaType)
    {
        return "    public String handlePost(String pathInfo, Map<String,String> params, String mediaType)";
    }

    public String handleDelete(String pathInfo, String mediaType)
    {

        return "    public String handleDelete(String pathInfo, String mediaType)";
    }



    /** temporary delegates **/

    public String handleGetById(Class clazz, Object id, String mediaType) {
        try {
            return marshaller.serialize(service.handleGetById(clazz, id, mediaType), OutputType.JSON);
        } catch (RemotingError remotingError) {
            try {
                return marshaller.serialize(remotingError, OutputType.JSON);
            } catch (RemotingError remotingError1) {
                remotingError1.printStackTrace();
                return "AN UNEXPECTED ERROR HAS OCCURRED";
            }
        }

    }

    public String handleGetAll(Class clazz, String mediaType) {

        try {
            return marshaller.serialize(
            service.handleGetAll(clazz, mediaType), OutputType.JSON);
        } catch (RemotingError remotingError) {

            try {
                return marshaller.serialize(remotingError, OutputType.JSON);
            } catch (RemotingError remotingError1) {
                remotingError1.printStackTrace();
                return "AN UNEXPECTED ERROR HAS OCCURRED";
            }
        }
    }

    public String handleSearch(Class clazz, Map<String, String> attributes, String mediaType) {
        return service.handleSearch(clazz, attributes, mediaType);
    }

    public String handlePostOnObject(Class clazz, Object id, Map<String, String> attributes, String mediaType) {
        return service.handlePostOnObject(clazz, id, attributes, mediaType);
    }

    public String handlePostOnResource(Class clazz, String data, String mediaType) {
        return service.handlePostOnResource(clazz, data, mediaType);
    }

    public String handlePutOnObject(Class clazz, Object id, Map<String, String> attributes, String mediaType) {
        return service.handlePutOnObject(clazz, id, attributes, mediaType);
    }

    public String handlePutOnResource(Class clazz, String data, String mediaType) {
        return service.handlePutOnResource(clazz, data, mediaType);
    }

    public String handleDeleteOnObject(Class clazz, Object id, String mediaType) {
        return service.handleDeleteOnObject(clazz, id, mediaType);
    }

    public String handleDeleteOnResource(Class clazz, String mediaType) {
        return service.handleDeleteOnResource(clazz, mediaType);
    }
}
