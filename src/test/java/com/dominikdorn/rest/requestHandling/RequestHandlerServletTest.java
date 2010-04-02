package com.dominikdorn.rest.requestHandling;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.services.ObjectRegistry;
import com.dominikdorn.tuwien.evs.rest.domain.Item;
import com.dominikdorn.tuwien.evs.rest.domain.Placement;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RequestHandlerServletTest {

    ServletContext context;
    HttpServletRequest request;
    HttpServletResponse response;

    ServletOutputStream out;

    Invoker invoker;
    ObjectRegistry registry;

    RequestHandlerServlet servlet;

    public RequestHandlerServletTest() {
    }

    @Before
    public void setup() throws IOException {
        context = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getProtocol()).thenReturn("HTTP 1.1");


        out = mock(ServletOutputStream.class);

        when(response.getOutputStream()).thenReturn(out);

        invoker = mock(Invoker.class);
        registry = new ObjectRegistry();

        Map<String, Class> objectDatabase = new HashMap<String, Class>();
        objectDatabase.put("items", Item.class);
        objectDatabase.put("racks", Rack.class);
        objectDatabase.put("placements", Placement.class);
        registry.setObjectDatabase(objectDatabase);


        servlet = new RequestHandlerServlet();
        servlet.setInvoker(invoker);
        servlet.setRegistry(registry);
    }

    @Test
    public void testGet_invalidUrl() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/adfads");
        when(request.getRequestURI()).thenReturn("/test/api/adfads");


        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testGet_invalidId() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/items/abc");
        when(request.getRequestURI()).thenReturn("/test/api/items/abc");

        servlet = new RequestHandlerServlet();

        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    public void testGet_validId_shouldCallInvoker() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/items/1");
        when(request.getRequestURI()).thenReturn("/test/api/items/1");
        when(request.getHeader("Accept")).thenReturn("application/xml");


        servlet.doGet(request, response);

        verify(invoker).handleGetById(Item.class, (Long) 1l, "application/xml");


        verify(response, never()).sendError(anyInt());


    }

    @Test
    public void evaluateRequestTest_resource_withWrongParam() {
        String input = "/items/adfadsf";

        OperationType type = servlet.evaluateRequest(input);
        assertEquals(OperationType.BAD_REQUEST, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithoutSlash() {
        String input = "/items";

        OperationType type = servlet.evaluateRequest(input);
        assertEquals(OperationType.ONLY_RESOURCE, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithSlash() {
        String input = "/items/";

        OperationType type = servlet.evaluateRequest(input);
        assertEquals(OperationType.ONLY_RESOURCE, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithId() {
        String input = "/items/1";

        OperationType type = servlet.evaluateRequest(input);
        assertEquals(OperationType.SPECIFIC_ID, type);
    }

    @Test
    public void evaluateRequestTest_resourceSearch() {
        String input = "/items/search";

        OperationType type = servlet.evaluateRequest(input);
        assertEquals(OperationType.SEARCH, type);
    }


    @Test
    public void matcherTest() {


        String input = "/items/1";

        Pattern genericMatch = Pattern.compile("^/([a-zA-Z0-9]+)/.*$");

        Matcher m = genericMatch.matcher(input);
        assertTrue(m.matches());
    }

    @Test
    public void getResourceName_PathWithoutSlash() {
        String path = "/items";
        OperationType type = servlet.evaluateRequest(path);
        assertEquals("items", servlet.getResourceName(type, path));
    }


    @Test
    public void getResourceName_PathWitSlash() {
        String path = "/items/";
        OperationType type = servlet.evaluateRequest(path);
        assertEquals("items", servlet.getResourceName(type, path));
    }

    @Test
    public void getResourceName_Searching() {
        String path = "/items/search";
        OperationType type = servlet.evaluateRequest(path);
        assertEquals("items", servlet.getResourceName(type, path));
    }

    @Test
    public void getResourceName_specificId() {
        String path = "/items/5";
        OperationType type = servlet.evaluateRequest(path);
        assertEquals("items", servlet.getResourceName(type, path));
    }


}
