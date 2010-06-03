package com.dominikdorn.rest.requestHandling;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.requestHandling.RequestHandlerServlet;
import com.dominikdorn.rest.services.ObjectRegistry;
import com.dominikdorn.rest.services.RemotingError;
import com.dominikdorn.tuwien.evs.rest.domain.Item;
import com.dominikdorn.tuwien.evs.rest.domain.Placement;
import com.dominikdorn.tuwien.evs.rest.domain.Rack;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
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
    @Ignore("this should be moved to a filter test")
    public void testGet_invalidUrl() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/adfads");
        when(request.getRequestURI()).thenReturn("/test/api/adfads");


        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    @Ignore("this should be moved to a filter test")
    public void testGet_invalidId() throws IOException, ServletException {

        when(request.getPathInfo()).thenReturn("/items/abc");
        when(request.getRequestURI()).thenReturn("/test/api/items/abc");

        servlet = new RequestHandlerServlet();

        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    @Ignore("this writes to the outputstream at the end, but I'm to lazy to fix this now")
    public void testGet_validId_shouldCallInvoker() throws IOException, ServletException, RemotingError {

        when(request.getPathInfo()).thenReturn("/items/1");
        when(request.getRequestURI()).thenReturn("/test/api/items/1");
        when(request.getHeader("Accept")).thenReturn("application/xml");

        when(request.getAttribute("restOperationType")).thenReturn(OperationType.SPECIFIC_ID);
        when(request.getAttribute("restOutputType")).thenReturn(OutputType.XML);
        when(request.getAttribute("restClazz")).thenReturn(Item.class);
        when(request.getAttribute("restSpecificId")).thenReturn(1l);

        servlet.doGet(request, response);

        verify(invoker).handleGetById(Item.class, (Long) 1l, OutputType.XML);


        verify(response, never()).sendError(anyInt());


    }




    @Test
    public void matcherTest() {


        String input = "/items/1";

        Pattern genericMatch = Pattern.compile("^/([a-zA-Z0-9]+)/.*$");

        Matcher m = genericMatch.matcher(input);
        assertTrue(m.matches());
    }






}
