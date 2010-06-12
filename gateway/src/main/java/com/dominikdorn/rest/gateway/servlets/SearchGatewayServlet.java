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
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // TODO get all storages
        // search them...
        // aggregate response
        // return it..

        String criteria = req.getParameter("criteria");
        System.out.println("Parameters: " + req.getParameterMap().toString());
        System.out.println(criteria);

        if (criteria != null) {

            List<String> clients = this.registry.getClients();

            if (clients.size() == 1) { // location index
                String index = clients.get(0);
                clients = Utilities.getClients(index, this.marshaller);
                for (final String client : clients) {
                    System.out.println("Contacting storage: " + client);
                    HttpResponse response = Utilities.search(client + "/api/items", criteria);
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
                            reader.close();

                            System.out.println("Response: " + resp.toString());
                            PrintWriter out = res.getWriter();
                            out.print(resp.toString());
                            
                            // System.out.println(Arrays.deepToString(response.getAllHeaders()));

                            // result.addAll((List<String>)
                            // marshaller.deSerialize(resp.toString(),
                            // List.class,
                            // OutputType.JSON));

                        }
                    }

                }
            }
        }

    }

}
