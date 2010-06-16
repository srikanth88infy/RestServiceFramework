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
import com.dominikdorn.rest.services.OutputType;
import com.dominikdorn.rest.utils.Utilities;

public class SearchGatewayServlet extends HttpServlet {

    private static final long serialVersionUID = -2203847223105868960L;

    private ClientRegistry registry;

    private Marshaller marshaller;

    private EncodingNegotiator negotiator;

    private String addr;

    private String port;

    private XMLResultGenerator gen;

    @Override
    public void init() throws ServletException {
        super.init();

        this.registry = (ClientRegistry) this.getServletContext().getAttribute("clientRegistry");
        this.gen = new XMLResultGenerator();

        this.addr = (String) System.getProperty("gateway.external.host");
        this.port = (String) System.getProperty("gateway.external.port");

        if (this.addr == null) {
            throw new ServletException("Could not obtain own address");
        }

        if (this.port == null) {
            throw new ServletException("Could not obtain own port");
        }

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

        PrintWriter out = res.getWriter();
        String result = "";

        if (this.negotiator.detect(accept).equals(OutputType.XML)) {
            result = this.serviceXML(criteria, accept);
        } else if (this.negotiator.detect(accept).equals(OutputType.JSON)) {
            result = this.serviceJSON(criteria, accept);
        } else {
            result = "Unsupported Output Type. Please specify a correct 'Accept' Header";
        }

        out.print(result);

    }

    private String serviceJSON(String criteria, String accept) throws IOException {
        List<String> clients = this.registry.getClients();
        if (criteria != null) {

            if (clients.size() == 1) { // location index
                Date gStart = new Date();
                String index = clients.get(0);

                if (Utilities.ping(index)) {
                    clients = Utilities.getClients(index, this.marshaller);

                    for (final String client : clients) {
                        Date start = new Date();

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

                                    reader.close();
                                }
                            } else {

                            }

                        } else {
                            System.out.println("Storage at " + client + " is not responding");

                        }

                        Date end = new Date();
                        final long qos = end.getTime() - start.getTime();
                    }

                } else {
                    System.out.println("Location Index at " + index + " is not responding");
                }

                Date gEnd = new Date();
                return null;
            }

            return null;

        } else {
            return null;
        }
    }

    private String serviceXML(String criteria, String accept) throws IOException {

        List<String> clients = this.registry.getClients();
        Document document = this.gen.createDocument();
        Element root = this.gen.createRoot(document, this.addr, this.port);
        if (criteria != null) {

            if (clients.size() == 1) { // location index
                Date gStart = new Date();
                String index = clients.get(0);

                if (Utilities.ping(index)) {
                    clients = Utilities.getClients(index, this.marshaller);

                    for (final String client : clients) {
                        Date start = new Date();
                        Element storage = gen.addStorage(root, client.substring(0, client.indexOf(':')), client
                            .substring(client.indexOf(':') + 1));

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

                                    reader.close();

                                    this.gen.addResultToStorage(storage, resp.toString());

                                }
                            } else {
                                this.gen.addErrorElement(storage, "An Exception occurred during the search!");
                            }

                        } else {
                            System.out.println("Storage at " + client + " is not responding");
                            this.gen.addErrorElement(storage, "Storage is not responding");

                        }

                        Date end = new Date();
                        final long qos = end.getTime() - start.getTime();
                        storage.addAttribute("time", Utilities.formatTime(qos));
                    }

                } else {
                    System.out.println("Location Index at " + index + " is not responding");
                    this.gen.addErrorElement(root, "LocationIndex is not responding");
                }

                Date gEnd = new Date();
                root.addAttribute("time", Utilities.formatTime(gEnd.getTime() - gStart.getTime()));
                return root.asXML();
            }

            this.gen.addErrorElement(root, "Could not obtain the Location index!").asXML();
            return root.asXML();

        } else {
            this.gen.addErrorElement(root, "No Search Criteria specified!");
            return root.asXML();
        }

    }
}
