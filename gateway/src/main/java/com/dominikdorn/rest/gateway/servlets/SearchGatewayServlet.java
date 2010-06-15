package com.dominikdorn.rest.gateway.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.services.EncodingNegotiator;
import com.dominikdorn.rest.utils.Utilities;

public class SearchGatewayServlet extends HttpServlet {

    private static final long serialVersionUID = -2203847223105868960L;

    private ClientRegistry registry;

    private Marshaller marshaller;

    private EncodingNegotiator negotiator;

    private String addr;

    private String port;

    @Override
    public void init() throws ServletException {
        super.init();

        this.registry = (ClientRegistry) this.getServletContext().getAttribute("clientRegistry");
        
        this.addr = (String) System.getProperty("gateway.external.host");
        this.port = (String) System.getProperty("gateway.external.port");

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
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String criteria = req.getParameter("criteria");
        String accept = req.getHeader("Accept");

        if (criteria != null) {

            List<String> clients = this.registry.getClients();

            if (clients.size() == 1) { // location index
                String index = clients.get(0);

                if (Utilities.ping(index)) {
                    clients = Utilities.getClients(index, this.marshaller);

                    for (final String client : clients) {

                        if (Utilities.ping(client)) {
                            System.out.println("Contacting storage: " + client);
                            HttpResponse response = Utilities.search(client + "/api/items", criteria, accept);
                            if (response != null) {
                                HttpEntity entity = response.getEntity();
                                if (entity != null) {
                                    final StringBuffer resp = new StringBuffer();
                                    final InputStream in = entity.getContent();
                                    final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    String line;
                                    
                                    while ((line = reader.readLine()) != null) {
                                        resp.append(line);
                                    }
                                    
                                    if (accept != null && accept.contains("application/xml")) {
                                        resp.insert(0, "<xml>");
                                        resp.append("</xml>");
                                    }
                                    
                                    resp.insert(0, "<p>Results from: <em>" + this.addr + ":" + this.port + "/" + index + "/" + client + "</em></p>");
                                    
                                    reader.close();

                                    //System.out.println("Response: " + resp.toString());
                                    PrintWriter out = res.getWriter();
                                    out.print(resp.toString());
                                    
                                }
                            }
                        } else {
                            System.out.println("Storage at " + client + " is not responding");
                        }
                    }
                } else {
                    System.out.println("Location Index at " + index + " is not responding");
                }
            }
        }

    }

}
