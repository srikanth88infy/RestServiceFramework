package com.dominikdorn.rest.servlets;

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
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String childHost = req.getParameter("host");
        String childPort = req.getParameter("port");


        String realHost = req.getRemoteHost();
        String realPort = String.valueOf(req.getRemotePort());

        System.out.println("registering a new child host with the following data: ");

        System.out.println("childHost = " + childHost);
        System.out.println("childPort = " + childPort);

        System.out.println("realHost = " + realHost);
        System.out.println("realPort = " + realPort);
        System.out.println("======================================");

        
        resp.setStatus(200);
        resp.getOutputStream().print("OK");
        resp.getOutputStream().close();
    }
}
