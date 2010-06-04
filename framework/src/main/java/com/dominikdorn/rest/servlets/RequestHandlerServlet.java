package com.dominikdorn.rest.servlets;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.requestHandling.OperationType;
import com.dominikdorn.rest.services.ObjectRegistry;
import com.dominikdorn.rest.services.OutputType;
import com.dominikdorn.rest.services.RemotingError;
import com.dominikdorn.rest.services.UnsupportedOperationRemotingError;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RequestHandlerServlet extends HttpServlet {

    ObjectRegistry registry;
    Invoker invoker;

    public ObjectRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(ObjectRegistry registry) {
        this.registry = registry;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OperationType type = (OperationType) req.getAttribute("restOperationType");
        OutputType outputType = (OutputType) req.getAttribute("restOutputType");
        Class clazz = (Class) req.getAttribute("restClazz");

        String response = "NOT PROCESSED - ERROR (doGet)";
        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                Long id = (Long) req.getAttribute("restSpecificId");
                try {
                    response = invoker.handleGetById(clazz, id, outputType);
                } catch (RemotingError remotingError) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            break;
            case ONLY_RESOURCE:
                try {
                    response = invoker.handleGetAll(clazz, outputType);
                } catch (RemotingError remotingError) {
                    remotingError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
        }
        if("ERROR".equals(response))
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(response.getBytes());
        }

    }


    @Override
    // OPERATION: UPDATE
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OperationType type = (OperationType) req.getAttribute("restOperationType");
        OutputType outputType = (OutputType) req.getAttribute("restOutputType");
        Class clazz = (Class) req.getAttribute("restClazz");

        String response = "NOT PROCESSED - ERROR (doPut)";

        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                Long id = (Long) req.getAttribute("restSpecificId");
                try {
                    response = invoker.handlePutOnObject(clazz, id, req.getParameterMap(), outputType);
                } catch (RemotingError remotingError) {
                    remotingError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            break;

            case ONLY_RESOURCE:
                try {
                    response = invoker.handlePutOnResource(clazz, req.getQueryString(), outputType);
                }
                catch (UnsupportedOperationRemotingError re)
                {
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                }
                catch (RemotingError remotingError) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
        }
        if("ERROR".equals(response))
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(response.getBytes());
        }
    }


    @Override
    // OPERATION CREATE
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OperationType type = (OperationType) req.getAttribute("restOperationType");
        OutputType outputType = (OutputType) req.getAttribute("restOutputType");
        Class clazz = (Class) req.getAttribute("restClazz");

        String response = "NOT PROCESSED - ERROR (doPost)";

        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                Long id = (Long) req.getAttribute("restSpecificId");
                try {
                    response = invoker.handlePostOnObject(clazz, id, req.getParameterMap(), outputType);
                } catch (RemotingError remotingError) {
                    resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
            break;
            case SEARCH: {
                Map<String, String> searchParams = new HashMap<String, String>();
                Set<String> searchableFields = registry.getSearchableFields(clazz);
                if (searchableFields == null || searchableFields.isEmpty()) {
                    response = "This entity does not have any searchable fields";
                    // TODO
                    // Create a remotingError here
                } else {
                    for (String field : searchableFields) {
                        String currentValue = req.getParameter(field);
                        if (currentValue != null) {
                            searchParams.put(field, currentValue);
                        }
                    }
                    if (searchParams.isEmpty()) {
                        response = "You haven't provided fields to search for. Available fields are \n" +
                                searchableFields.toString();
                        // TODO
                        // Create a remotingError here
                    } else {
                        try {
                            response = invoker.handleSearch(clazz, searchParams,outputType);
                        } catch (RemotingError remotingError) {
                            remotingError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
            break;
            case ONLY_RESOURCE:
                try {
                    response = invoker.handlePostOnResource(clazz, req.getParameterMap(), outputType);
                } catch (RemotingError remotingError) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
        }
        if("ERROR".equals(response))
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(response.getBytes());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OperationType type = (OperationType) req.getAttribute("restOperationType");
        OutputType outputType = (OutputType) req.getAttribute("restOutputType");
        Class clazz = (Class) req.getAttribute("restClazz");

        String response = "NOT PROCESSED - ERROR (doDelete)";

        /*** everything the same until here **/

        switch (type) {
            case SPECIFIC_ID: {
                Long id = (Long) req.getAttribute("restSpecificId");
                try {
                    invoker.handleDeleteOnObject(clazz, id, outputType);
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (RemotingError remotingError) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            break;
            case SEARCH: {
                // NOT TO BE IMPLEMENTED
            }
            break;
            case ONLY_RESOURCE:
                try{
                    response = invoker.handleDeleteOnResource(clazz, outputType);
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
                } catch (RemotingError e)
                {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
        if("ERROR".equals(response))
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(response.getBytes());
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        registry = (ObjectRegistry) config.getServletContext().getAttribute("restObjectRegistry");
        invoker = (Invoker) config.getServletContext().getAttribute("restInvoker");

        if (registry == null || invoker == null)
            throw new ServletException("The Rest-Framework is not correctly setup. \n" +
                    "Have you added the ApplicationStartupListener to pre Servlet 3.0 Web.xml files?");
    }
}
