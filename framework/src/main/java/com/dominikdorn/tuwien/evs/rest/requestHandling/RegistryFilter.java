package com.dominikdorn.tuwien.evs.rest.requestHandling;

import com.dominikdorn.tuwien.evs.rest.services.ObjectRegistry;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegistryFilter implements Filter {
    ObjectRegistry registry;

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String resourceName = (String) req.getAttribute("restResourceName");
        if (resourceName == null || !registry.isRegisteredItemName(resourceName)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.flushBuffer();
            return;
        }
        Class clazz = registry.getRegisteredClass(resourceName);
        req.setAttribute("restClazz", clazz);
        
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
       registry = (ObjectRegistry) config.getServletContext().getAttribute("restObjectRegistry");
    }

}
