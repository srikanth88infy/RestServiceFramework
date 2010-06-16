package com.dominikdorn.rest.externalservice.servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
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
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String criteria = req.getParameter("criteria");
        System.out.println("Search for: " + criteria);
        System.out.println("Getting Locations from MetaService...");
        final String addr = this.metaAddr + ":" + this.metaPort;

        if (Utilities.ping(addr)) {
            
            final Date start = new Date();
            List<String> clients = Utilities.getClients(addr, this.marshaller);
            String response = "";
            PrintWriter out = res.getWriter();
            out.println("<html><head><title>Search</title></head>");
            out.println("<body><h1>External Service Search</h1>");

            System.out.println(addr);
            for (String client : clients) {

                if (client.startsWith("10")) { //ugly hack
                    continue;
                } else {

                    System.out.println("Contacting: " + client);

                    if (Utilities.ping(client)) {
                        try {
                            URL url = new URL("http://" + client + "/search");
                            URLConnection con = url.openConnection();

                            con.setDoOutput(true);
                            con.setDoInput(true);
                            con.setUseCaches(false);
                            con.setRequestProperty("Content-type", req.getContentType());
                            con.setRequestProperty("Content-length", "" + req.getContentLength());
                            con.setRequestProperty("Accept", req.getHeader("Accept"));

                            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                            dos.writeBytes("criteria=" + criteria);
                            dos.close();

                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String line;
                            

                            while ((line = in.readLine()) != null) {
                                response = response + line;
                            }

                            in.close();
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println(client + " is not responding!");
                    }
                }
            }
            final Date end = new Date();
            final long qos = end.getTime() - start.getTime();
            
            System.out.println(Utilities.formatTime(qos));
            out.println("<p>Request duration: " + Utilities.formatTime(qos) +  "</p>");
            out.println(response);
            out.println("</body></html>");
            
            
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
