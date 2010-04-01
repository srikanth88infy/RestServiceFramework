package com.dominikdorn.rest.requestHandling;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.services.ObjectRegistry;

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
public class RequestHandlerServlet extends HttpServlet {

    ObjectRegistry registry;
    Invoker invoker;

    public ObjectRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(ObjectRegistry registry) {
        this.registry = registry;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);


    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.  See {@link javax.servlet.Servlet#init}.
     * <p/>
     * <p>This implementation stores the {@link javax.servlet.ServletConfig}
     * object it receives from the servlet container for later use.
     * When overriding this form of the method, call
     * <code>super.init(config)</code>.
     *
     * @param config the <code>ServletConfig</code> object
     *               that contains configutation
     *               information for this servlet
     * @throws javax.servlet.ServletException if an exception occurs that
     *                                        interrupts the servlet's normal
     *                                        operation
     * @see javax.servlet.UnavailableException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        registry = (ObjectRegistry) config.getServletContext().getAttribute("restObjectRegistry");
        invoker = (Invoker) config.getServletContext().getAttribute("restInvoker");

        if(registry ==  null || invoker == null)
            throw new ServletException("The Rest-Framework is not correctly setup. \n" +
                    "Have you added the ApplicationStartupListener to pre Servlet 3.0 Web.xml files?");
    }
}
