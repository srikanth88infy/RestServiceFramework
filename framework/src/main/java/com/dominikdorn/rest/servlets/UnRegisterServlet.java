package com.dominikdorn.rest.servlets;

import com.dominikdorn.rest.registration.ClientRegistry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class UnRegisterServlet extends HttpServlet {

    private ClientRegistry registry;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String childHost = req.getParameter("host");
        String childPort = req.getParameter("port");

        String realHost = req.getRemoteHost();
        String realPort = String.valueOf(req.getRemotePort());

        System.out.println("un-registering a new child host with the following data: ");

        System.out.println("childHost = " + childHost);
        System.out.println("childPort = " + childPort);

        System.out.println("realHost = " + realHost);
        System.out.println("realPort = " + realPort);
        System.out.println("======================================");

       try {
            registry.removeClient(childHost, childPort);
            resp.setStatus(200);
            resp.getOutputStream().print("OK");
            resp.getOutputStream().close();
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getOutputStream().print("FAILED to remove your client to our database. Please see Server logs");
            resp.getOutputStream().close();
            e.printStackTrace();
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        registry = (ClientRegistry) config.getServletContext().getAttribute("clientRegistry");
        if (registry == null)
            throw new ServletException("ClientRegistry not initialized");
    }
}
