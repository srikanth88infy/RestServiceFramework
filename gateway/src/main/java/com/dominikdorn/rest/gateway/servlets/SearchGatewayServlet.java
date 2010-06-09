package com.dominikdorn.rest.gateway.servlets;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.services.EncodingNegotiator;
import com.dominikdorn.rest.utils.Utilities;

public class SearchGatewayServlet extends HttpServlet{

    private static final long serialVersionUID = -2203847223105868960L;
   
    private ClientRegistry registry;

    private Marshaller marshaller;

    private EncodingNegotiator negotiator;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        this.registry = (ClientRegistry) this.getServletContext().getAttribute("clientRegistry");
        
        if (this.registry == null) {
            throw new ServletException("Could not obtain the client registry!");
        }

        this.marshaller = (Marshaller) this.getServletContext().getAttribute("restMarshaller");
        this.negotiator = (EncodingNegotiator) this.getServletContext().getAttribute("restEncodingNegotiator");

        if (this.marshaller == null) {
            throw new ServletException("Marshaller could not be initialized!");
        }

        if (this.negotiator == null) {
            throw new ServletException("Negotiator could not be initialized");
        }

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        //TODO get all storages
        //search them...
        //aggregate response
        //return it..
        
        String criteria = req.getParameter("criteria");
        System.out.println("Parameters: " + req.getParameterMap().toString());
        System.out.println(criteria);
        List<String> clients = this.registry.getClients();
        
        if (clients.size() == 1) { //location index
            String index = clients.get(0);
            clients = Utilities.getClients(index, this.marshaller);
            for (final String client : clients) {
                System.out.println("Contacting storage: " + client);
                Utilities.search(client + "/api/items", criteria);
                
            }
        }
        
       
    }


}
