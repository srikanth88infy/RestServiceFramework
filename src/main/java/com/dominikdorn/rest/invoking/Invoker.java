package com.dominikdorn.rest.invoking;

import com.dominikdorn.rest.marshalling.Marshaller;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 * This class is the Invoker. It takes the data given from the RequestHandler,
 * matches the given MediaType (String) to a specific OutputType, invokes the
 * marshaller for Object translation and then invokes the specific service method.
 */
public class Invoker {

    private Marshaller marshaller;

    public Invoker(Marshaller marshaller) {
        this.marshaller = marshaller;
    }


    public void handleGet(String data, String mediaType){

    }

    public void handlePost(String data, String mediaType){

    }

    public void handlePut(String data, String mediaType){

    }

    public void handleDelete(String data, String mediaType){

    }
}
