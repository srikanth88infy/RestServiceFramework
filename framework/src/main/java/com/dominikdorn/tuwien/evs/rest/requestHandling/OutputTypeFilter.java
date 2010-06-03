package com.dominikdorn.tuwien.evs.rest.requestHandling;

import com.dominikdorn.tuwien.evs.rest.services.EncodingNegotiator;
import com.dominikdorn.tuwien.evs.rest.services.OutputType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class OutputTypeFilter implements Filter {
    EncodingNegotiator negotiator;
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        OutputType outputType = negotiator.detect(req.getHeader("Accept"));
        if(OutputType.UNSUPPORTED.equals(outputType))
        {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }

        req.setAttribute("restOutputType", outputType);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        negotiator = (EncodingNegotiator) config.getServletContext().getAttribute("restEncodingNegotiator");

    }

}
