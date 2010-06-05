package com.dominikdorn.rest.servlets;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.services.EncodingNegotiator;
import com.dominikdorn.rest.services.OutputType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class GetClientsServlet extends HttpServlet {


    ClientRegistry registry;
    Marshaller marshaller;
    EncodingNegotiator negotiator;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        try{
        List<String> clients = registry.getClients();

        OutputType type = negotiator.detect(req.getContentType());
        String result = marshaller.serialize(clients, java.util.List.class, type);

        resp.setStatus(200);
        resp.getOutputStream().print(result);
        resp.getOutputStream().close();
        }
        catch (Exception e)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getOutputStream().print("An error has occured, please see the servers log files");
            resp.getOutputStream().close();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.registry = (ClientRegistry) config.getServletContext().getAttribute("clientRegistry");

        if(registry == null)
            throw new ServletException("ClientRegistry is not set. Please add the ClientRegistryListener to your web.xml");

        this.marshaller = (Marshaller) config.getServletContext().getAttribute("restMarshaller");

        if(marshaller == null)
            throw new ServletException("The Marshaller is null. Please make sure, that the MarshallerListener is loaded. ");

        this.negotiator = (EncodingNegotiator) config.getServletContext().getAttribute("restEncodingNegotiator");

        if(negotiator == null)
            throw new ServletException("The EncodingNegotiator is null. Please make sure, that the EncodingNegotiatorListener is loaded. ");       
    }

    @Override
    public void destroy() {
        this.registry = null;
        this.marshaller = null;
        this.negotiator = null;
    }
}
