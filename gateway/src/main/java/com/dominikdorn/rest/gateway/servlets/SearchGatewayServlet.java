package com.dominikdorn.rest.gateway.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.dom4j.Document;
import org.dom4j.Element;

import com.dominikdorn.rest.gateway.utils.XMLResultGenerator;
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
                Date gStart = new Date();
                String index = clients.get(0);
                
                final XMLResultGenerator gen = new XMLResultGenerator();
                Document document = gen.createDocument();
                Element root = gen.createRoot(document, this.addr, this.port);
                
                if (Utilities.ping(index)) {
                    clients = Utilities.getClients(index, this.marshaller);

                    for (final String client : clients) {

                        if (Utilities.ping(client)) {
                            System.out.println("Contacting storage: " + client);
                            Date start = new Date();
                            HttpResponse response = Utilities.search(client + "/api/items", criteria, accept);
                            Date end = new Date();
                            final long qos = end.getTime() - start.getTime();

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
                                    
                                    Element storage = gen.addStorage(root, client.substring(0, client.indexOf(':')), client
                                        .substring(client.indexOf(':') + 1), Utilities.formatTime(qos));
                                    gen.addResultToStorage(storage, resp.toString());
                                    
                                }
                            }
                        } else {
                            System.out.println("Storage at " + client + " is not responding");
                        }
                    }
                } else {
                    System.out.println("Location Index at " + index + " is not responding");
                }
                
                Date gEnd = new Date();
                root.addAttribute("time", Utilities.formatTime(gEnd.getTime() - gStart.getTime()));
                PrintWriter out = res.getWriter();
                out.print(document.getRootElement().asXML());
            }
        }

    }
}
