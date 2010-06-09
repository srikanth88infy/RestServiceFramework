package com.dominikdorn.rest.externalservice.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.services.EncodingNegotiator;
import com.dominikdorn.rest.utils.Utilities;

public class ExternalServiceServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1905833925746787923L;

    private String metaPort;

    private String metaAddr;

    private Marshaller marshaller;

    private EncodingNegotiator negotiator;

    @Override
    public void init() throws ServletException {
        super.init();

        this.metaAddr = (String) System.getProperties().get("metaService.external.host");
        this.metaPort = (String) System.getProperties().get("metaService.external.port");

        this.marshaller = (Marshaller) this.getServletContext().getAttribute("restMarshaller");

        this.negotiator = (EncodingNegotiator) this.getServletContext().getAttribute("restEncodingNegotiator");

        if (this.metaAddr == null || this.metaPort == null) {
            throw new ServletException("No location of the meta service provided!");
        }

        if (this.marshaller == null) {
            throw new ServletException("Marshaller could not be initialized!");
        }

        if (this.negotiator == null) {
            throw new ServletException("Negotiator could not be initialized");
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) {

        String criteria = req.getParameter("criteria");
        System.out.println("Search for: " + criteria);
        System.out.println("Getting Locations from MetaService...");
        final String addr = this.metaAddr + ":" + this.metaPort;

        if (Utilities.ping(addr)) {

            List<String> clients = Utilities.getClients(addr, this.marshaller);
            final Date start = new Date();

            for (String client : clients) {

                System.out.println("Contacting: " + client);

                if (Utilities.ping(client)) {
                    Utilities.search(client, criteria);
                } else {
                    System.err.println(client + " is not responding!");
                }
            }

            final Date end = new Date();
        } else {
            final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/404.xhtml");
            try {
                dispatcher.forward(req, res);
            } catch (ServletException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

}
