package com.dominikdorn.tuwien.evs.rest.requestHandling;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class OperationTypeFilter implements Filter {
    public void destroy() {
    }

    Pattern findAll = Pattern.compile("^/([a-zA-Z0-9]+)(/?)");
    Pattern specificId = Pattern.compile("^/([a-zA-Z0-9]+)/([0-9]+)");

    Pattern search = Pattern.compile("^/([a-zA-Z0-9]+)/search");
    Pattern genericMatchWithoutSlash = Pattern.compile("^/([a-zA-Z0-9]+)");
    Pattern genericMatchWithSlash = Pattern.compile("^/([a-zA-Z0-9]+)/");
    Pattern genericMatch = Pattern.compile("^/([a-zA-Z0-9]+)(/(([0-9]+)|search))?");


    public OperationType evaluateRequest(String pathInfo) {
        Matcher matcher;


        matcher = genericMatch.matcher(pathInfo);
        if (!matcher.matches()) {
            matcher = genericMatchWithoutSlash.matcher(pathInfo);
            if (!matcher.matches()) {
                matcher = genericMatchWithSlash.matcher(pathInfo);
                if (!matcher.matches()) {
                    return OperationType.BAD_REQUEST;
                }
            }
        }

        matcher = specificId.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.SPECIFIC_ID;
        }

        matcher = search.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.SEARCH;
        }

        matcher = findAll.matcher(pathInfo);
        if (matcher.matches()) {
            return OperationType.ONLY_RESOURCE;
        }


        return OperationType.BAD_REQUEST;
    }

    public String getResourceName(OperationType type, String pathInfo) {
        if (OperationType.ONLY_RESOURCE == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        if (OperationType.ONLY_RESOURCE == type) {
            Matcher matcher = genericMatchWithSlash.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }


        if (OperationType.SEARCH == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        if (OperationType.SPECIFIC_ID == type) {
            Matcher matcher = genericMatch.matcher(pathInfo);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        return null;
    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse resp = (HttpServletResponse) res;

        OperationType type = evaluateRequest(request.getPathInfo());
        String resourceName = getResourceName(type, request.getPathInfo());

        req.setAttribute("restOperationType", type);
        req.setAttribute("restResourceName", resourceName);

        if(type == OperationType.SPECIFIC_ID)
        {
            Matcher matcher = specificId.matcher(request.getPathInfo());
            matcher.matches();
            Long id = Long.parseLong(matcher.group(2));
            req.setAttribute("restSpecificId", id);
        }


        if (type == OperationType.INVALID_TYPE)
            throw new ServletException("An Invalid OperationType was found");

        if (type == OperationType.BAD_REQUEST) {
            // return 400 Bad Request error, as the syntax is incorrect.
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.flushBuffer();
            return;
        }



        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
