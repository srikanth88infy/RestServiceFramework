package com.dominikdorn.rest.requestHandling;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.services.ObjectRegistry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    Pattern findAll = Pattern.compile("^/([a-zA-Z0-9]+)(/?)");
    Pattern specificId = Pattern.compile("^/([a-zA-Z0-9]+)/([0-9]+)");
    Pattern search = Pattern.compile("^/([a-zA-Z0-9]+)/search");

    Pattern genericMatchWithoutSlash = Pattern.compile("^/([a-zA-Z0-9]+)");
    Pattern genericMatchWithSlash = Pattern.compile("^/([a-zA-Z0-9]+)/");
    Pattern genericMatch = Pattern.compile("^/([a-zA-Z0-9]+)(/(([0-9]+)|search))?");


    public OperationType evaluateRequest(String pathInfo) {
        Matcher matcher;


        matcher = genericMatch.matcher(pathInfo);
        if (!matcher.matches()) {
            matcher = genericMatchWithoutSlash.matcher(pathInfo);
            if (!matcher.matches()) {
                matcher = genericMatchWithSlash.matcher(pathInfo);
                if (!matcher.matches()) {
                    return OperationType.BAD_REQUEST;
                }
            }
        }

        matcher = specificId.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.SPECIFIC_ID;
        }

        matcher = search.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.SEARCH;
        }

        matcher = findAll.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.ONLY_RESOURCE;
        }


        return OperationType.BAD_REQUEST;
    }

    public String getResourceName(OperationType type, String pathInfo) {
        if (OperationType.ONLY_RESOURCE == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        if (OperationType.ONLY_RESOURCE == type) {
            Matcher matcher = genericMatchWithSlash.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }


        if (OperationType.SEARCH == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        if (OperationType.SPECIFIC_ID == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        return null;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        OperationType type = evaluateRequest(pathInfo);

        if (type == OperationType.INVALID_TYPE)
            throw new ServletException("An Invalid OperationType was found");

        if (type == OperationType.BAD_REQUEST) {
            // return 400 Bad Request error, as the syntax is incorrect.
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.flushBuffer();
            return;

        }

        String resourceName = getResourceName(type, pathInfo);

        if (resourceName == null || !registry.isRegisteredItemName(resourceName)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.flushBuffer();
            return;
        }
        Matcher matcher;
        Class clazz = registry.getRegisteredClass(resourceName);

        String response = "NOT PROCESSED - ERROR";
        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                matcher = specificId.matcher(pathInfo);
                matcher.matches();
                Long id = Long.parseLong(matcher.group(2));
                response = invoker.handleGetById(clazz, id, req.getHeader("Accept"));
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
                        response = invoker.handleSearch(clazz, searchParams, req.getHeader("Accept"));
                    }
                }
            }
            break;
            case ONLY_RESOURCE:
                response = invoker.handleGetAll(clazz, req.getHeader("Accept"));
                break;
        }
        resp.getOutputStream().print(response);
        resp.flushBuffer();
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        OperationType type = evaluateRequest(pathInfo);

        if (type == OperationType.INVALID_TYPE)
            throw new ServletException("An Invalid OperationType was found");

        if (type == OperationType.BAD_REQUEST) {
            // return 400 Bad Request error, as the syntax is incorrect.
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.flushBuffer();
            return;

        }

        String resourceName = getResourceName(type, pathInfo);

        if (resourceName == null || !registry.isRegisteredItemName(resourceName)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.flushBuffer();
            return;
        }
        Matcher matcher;
        Class clazz = registry.getRegisteredClass(resourceName);

        String response = "NOT PROCESSED - ERROR";
        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                matcher = specificId.matcher(pathInfo);
                matcher.matches();
                Long id = Long.parseLong(matcher.group(2));
                response = invoker.handlePutOnObject(clazz, id, req.getParameterMap(), req.getHeader("Accept"));
            }
            break;
            case SEARCH: {
                // NOT TO BE IMPLEMENTED
            }
            break;
            case ONLY_RESOURCE:
                response = invoker.handlePutOnResource(clazz, req.getQueryString(), req.getHeader("Accept"));
                break;
        }
        resp.getOutputStream().print(response);
        resp.flushBuffer();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        OperationType type = evaluateRequest(pathInfo);

        if (type == OperationType.INVALID_TYPE)
            throw new ServletException("An Invalid OperationType was found");

        if (type == OperationType.BAD_REQUEST) {
            // return 400 Bad Request error, as the syntax is incorrect.
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.flushBuffer();
            return;

        }

        String resourceName = getResourceName(type, pathInfo);

        if (resourceName == null || !registry.isRegisteredItemName(resourceName)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.flushBuffer();
            return;
        }
        Matcher matcher;
        Class clazz = registry.getRegisteredClass(resourceName);

        String response = "NOT PROCESSED - ERROR";
        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                matcher = specificId.matcher(pathInfo);
                matcher.matches();
                Long id = Long.parseLong(matcher.group(2));
                response = invoker.handlePostOnObject(clazz, id, req.getParameterMap(), req.getHeader("Accept"));
            }
            break;
            case SEARCH: {
                // NOT TO BE IMPLEMENTED
            }
            break;
            case ONLY_RESOURCE:
                response = invoker.handlePostOnResource(clazz, req.getQueryString(), req.getHeader("Accept"));
                break;
        }
        resp.getOutputStream().print(response);
        resp.flushBuffer();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        OperationType type = evaluateRequest(pathInfo);

        if (type == OperationType.INVALID_TYPE)
            throw new ServletException("An Invalid OperationType was found");

        if (type == OperationType.BAD_REQUEST) {
            // return 400 Bad Request error, as the syntax is incorrect.
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.flushBuffer();
            return;

        }

        String resourceName = getResourceName(type, pathInfo);

        if (resourceName == null || !registry.isRegisteredItemName(resourceName)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.flushBuffer();
            return;
        }
        Matcher matcher;
        Class clazz = registry.getRegisteredClass(resourceName);

        String response = "NOT PROCESSED - ERROR";
        /*** everything the same until here **/


        switch (type) {
            case SPECIFIC_ID: {
                // we are having a get request for a specific id
                // do something with it.
                matcher = specificId.matcher(pathInfo);
                matcher.matches();
                Long id = Long.parseLong(matcher.group(2));
                response = invoker.handleDeleteOnObject(clazz, id, req.getHeader("Accept"));
            }
            break;
            case SEARCH: {
                // NOT TO BE IMPLEMENTED
            }
            break;
            case ONLY_RESOURCE:
                response = invoker.handleDeleteOnResource(clazz, req.getHeader("Accept"));
                break;
        }
        resp.getOutputStream().print(response);
        resp.flushBuffer();
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.  See {@link javax.servlet.Servlet#init}.
     * <p/>
     * <p>This implementation stores the {@link javax.servlet.ServletConfig}
     * object it receives from the servlet container for later use.
     * When overriding this form of the method, call
     * <code>super.init(config)</code>.
     *
     * @param config the <code>ServletConfig</code> object
     *               that contains configutation
     *               information for this servlet
     * @throws javax.servlet.ServletException if an exception occurs that
     *                                        interrupts the servlet's normal
     *                                        operation
     * @see javax.servlet.UnavailableException
     */
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
