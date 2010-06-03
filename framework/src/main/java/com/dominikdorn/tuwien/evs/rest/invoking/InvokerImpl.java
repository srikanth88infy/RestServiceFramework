package com.dominikdorn.tuwien.evs.rest.invoking;

import com.dominikdorn.tuwien.evs.rest.converter.ConverterService;
import com.dominikdorn.tuwien.evs.rest.marshalling.Marshaller;
import com.dominikdorn.tuwien.evs.rest.services.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 * <p/>
 * This class is the Invoker. It takes the data given from the RequestHandler,
 * matches the given MediaType (String) to a specific OutputType, invokes the
 * marshaller for Object translation and then invokes the specific service method.
 * <p/>
 * <p/>
 * * Invoker(s) accept(s) invocations from requestors
 * * Invoker reads the request and demarshalls it
 * * Looks up the correct local object & operation and
 * * Performs the invocation on the targeted remote object
 */
public class InvokerImpl implements Invoker {

    private Marshaller marshaller;
    private ConverterService converterService;
    private RestService service;

    public InvokerImpl(Marshaller marshaller, RestService service, ConverterService converterService) {
        this.marshaller = marshaller;
        this.service = service;
        this.converterService = converterService;
    }

    /**
     * temporary delegates *
     */

    @Override
    public String handleGetById(Class clazz, Object id, OutputType mediaType) throws RemotingError {
        try {
            return marshaller.serialize(service.handleGetById(clazz, id), clazz, mediaType);
        } catch (RemotingError remotingError) {
            try {
                return marshaller.serialize(new RemotingErrorObject(remotingError.getType(), remotingError.getMessage()), clazz, mediaType);
            } catch (RemotingError remotingError1) {
                remotingError1.printStackTrace();
                return "ERROR";
            }
        }
    }

    @Override
    public String handleGetAll(Class clazz, OutputType mediaType) throws RemotingError {

        try {
            return marshaller.serialize(
                    service.handleGetAll(clazz), clazz, mediaType);
        } catch (RemotingError remotingError) {
            try {
                return marshaller.serialize(
                        new RemotingErrorObject(remotingError), clazz, mediaType);
            } catch (RemotingError remotingError1) {
                remotingError1.printStackTrace();
                return "ERROR";
            }
        }
    }

    @Override
    public String handleSearch(Class clazz, Map<String, String> attributes, OutputType mediaType) throws RemotingError {

        Object o = converterService.getConverter(clazz).getObject(attributes);

        Object result = service.handleSearch(clazz, attributes);
        return marshaller.serialize(result, clazz, mediaType);
    }

    // CREATE THIS OBJECT

    @Override
    public String handlePostOnObject(Class clazz, Object id, Map<String, String> attributes, OutputType mediaType) throws RemotingError {
        Object o = null;
        o = extractInput(clazz, attributes, mediaType);

        try {
            for (Method m : clazz.getMethods()) {
                if (m.getName().indexOf("setId") > -1) {
                    m.invoke(o, id);
                    break;
                }
            }
//            clazz.getMethod("setId", clazz).invoke(Long.getLong(id.toString() ), o);
//            clazz.getField("id").setLong(o, Long.getLong(id.toString()));
        } catch (IllegalAccessException e) {
            throw new ObjectPropertyRemotingError();
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return marshaller.serialize(service.handlePostOnObject(clazz, id, o), clazz, mediaType);
    }
    // CREATE A NEW OBJECT ON THE SPECIFIC RESOURCE

    @Override
    public String handlePostOnResource(Class clazz, Map<String, String> data, OutputType mediaType) throws RemotingError {


        Object o = null;
        o = extractInput(clazz, data, mediaType);


        return marshaller.serialize(service.handlePostOnResource(clazz, o), clazz, mediaType);
    }

    private Object extractInput(Class clazz, Map<String,String> data, OutputType mediaType){
        Object o = null;
        if (OutputType.XHTML.equals(mediaType)) {
            try {
                o = clazz.newInstance();
            } catch (InstantiationException e) {
                throw new ObjectCreationRemotingError();
            } catch (IllegalAccessException e) {
                throw new ObjectCreationRemotingError();
            }
            for (Map.Entry<String, String> e : data.entrySet()) {
                try {
                    clazz.getField(e.getKey()).set(o, e.getValue());
                } catch (IllegalAccessException e1) {
                    throw new ObjectPropertyRemotingError();
                } catch (NoSuchFieldException e1) {
                    throw new ObjectPropertyRemotingError();
                }
            }

        } else if (OutputType.JSON.equals(mediaType)) {
            String result = "";
            for (Map.Entry<String, String> e : data.entrySet()) {
                if (!e.getKey().isEmpty()) {
                    result = e.getKey();
                    break;
                }
            }

            o = marshaller.deSerialize(result, clazz, mediaType);

        } else if (OutputType.XML.equals(mediaType)) {
            String result = "";
            for (Map.Entry<String, String> e : data.entrySet()) {
                if (!e.getKey().isEmpty()) {
                    result = e.getKey() + "=" ;
                    Object val = e.getValue();
                    String[] values = (String[]) val;
                    for(String s : values)
                    {
                        result = result + s;
                    }
                    break;
                }
            }

            o = marshaller.deSerialize(result, clazz, mediaType);

        }
        return o;
    }

    @Override
    public String handlePutOnObject(Class clazz, Object id, Map<String, String> attributes, OutputType mediaType) throws RemotingError {

        Object o = null;
        o = extractInput(clazz, attributes, mediaType);


        try {
            for (Method m : clazz.getMethods()) {
                if (m.getName().indexOf("setId") > -1) {
                    m.invoke(o, id);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            throw new ObjectPropertyRemotingError();
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return marshaller.serialize(service.handlePutOnObject(clazz, id, o), clazz, mediaType);
    }

    @Override
    public String handlePutOnResource(Class clazz, String data, OutputType mediaType) throws RemotingError {
        // TODO create Object
        // supply it to service
        return service.handlePutOnResource(clazz, data);
    }

    @Override
    public void handleDeleteOnObject(Class clazz, Object id, OutputType mediaType) throws RemotingError {
        service.handleDeleteOnObject(clazz, id);
    }

    @Override
    public String handleDeleteOnResource(Class clazz, OutputType mediaType) throws RemotingError {
        return marshaller.serialize(service.handleDeleteOnResource(clazz), clazz, mediaType);
    }
}
