package com.dominikdorn.tuwien.evs.rest.servlets;

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

    /**
     * Called by the server (via the <code>service</code> method) to
     * allow a servlet to handle a GET request.
     * <p/>
     * <p>Overriding this method to support a GET request also
     * automatically supports an HTTP HEAD request. A HEAD
     * request is a GET request that returns no body in the
     * response, only the request header fields.
     * <p/>
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or
     * output stream object, and finally, write the response data.
     * It's best to include content type and encoding. When using
     * a <code>PrintWriter</code> object to return the response,
     * set the content type before accessing the
     * <code>PrintWriter</code> object.
     * <p/>
     * <p>The servlet container must write the headers before
     * committing the response, because in HTTP the headers must be sent
     * before the response body.
     * <p/>
     * <p>Where possible, set the Content-Length header (with the
     * {@link javax.servlet.ServletResponse#setContentLength} method),
     * to allow the servlet container to use a persistent connection
     * to return its response to the client, improving performance.
     * The content length is automatically set if the entire response fits
     * inside the response buffer.
     * <p/>
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header.
     * <p/>
     * <p>The GET method should be safe, that is, without
     * any side effects for which users are held responsible.
     * For example, most form queries have no side effects.
     * If a client request is intended to change stored data,
     * the request should use some other HTTP method.
     * <p/>
     * <p>The GET method should also be idempotent, meaning
     * that it can be safely repeated. Sometimes making a
     * method safe also makes it idempotent. For example,
     * repeating queries is both safe and idempotent, but
     * buying a product online or modifying data is neither
     * safe nor idempotent.
     * <p/>
     * <p>If the request is incorrectly formatted, <code>doGet</code>
     * returns an HTTP "Bad Request" message.
     *
     * @param req  an {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet
     * @param resp an {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client
     * @throws java.io.IOException            if an input or output error is
     *                                        detected when the servlet handles
     *                                        the GET request
     * @throws javax.servlet.ServletException if the request for the GET
     *                                        could not be handled
     * @see javax.servlet.ServletResponse#setContentType
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a PUT request.
     * <p/>
     * The PUT operation allows a client to
     * place a file on the server and is similar to
     * sending a file by FTP.
     * <p/>
     * <p>When overriding this method, leave intact
     * any content headers sent with the request (including
     * Content-Length, Content-Type, Content-Transfer-Encoding,
     * Content-Encoding, Content-Base, Content-Language, Content-Location,
     * Content-MD5, and Content-Range). If your method cannot
     * handle a content header, it must issue an error message
     * (HTTP 501 - Not Implemented) and discard the request.
     * For more information on HTTP 1.1, see RFC 2616
     * <a href="http://www.ietf.org/rfc/rfc2616.txt"></a>.
     * <p/>
     * <p>This method does not need to be either safe or idempotent.
     * Operations that <code>doPut</code> performs can have side
     * effects for which the user can be held accountable. When using
     * this method, it may be useful to save a copy of the
     * affected URL in temporary storage.
     * <p/>
     * <p>If the HTTP PUT request is incorrectly formatted,
     * <code>doPut</code> returns an HTTP "Bad Request" message.
     *
     * @param req  the {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client made of
     *             the servlet
     * @param resp the {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet returns
     *             to the client
     * @throws java.io.IOException            if an input or output error occurs
     *                                        while the servlet is handling the
     *                                        PUT request
     * @throws javax.servlet.ServletException if the request for the PUT
     *                                        cannot be handled
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a POST request.
     * <p/>
     * The HTTP POST method allows the client to send
     * data of unlimited length to the Web server a single time
     * and is useful when posting information such as
     * credit card numbers.
     * <p/>
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best
     * to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the
     * content type before accessing the <code>PrintWriter</code> object.
     * <p/>
     * <p>The servlet container must write the headers before committing the
     * response, because in HTTP the headers must be sent before the
     * response body.
     * <p/>
     * <p>Where possible, set the Content-Length header (with the
     * {@link javax.servlet.ServletResponse#setContentLength} method),
     * to allow the servlet container to use a persistent connection
     * to return its response to the client, improving performance.
     * The content length is automatically set if the entire response fits
     * inside the response buffer.
     * <p/>
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header.
     * <p/>
     * <p>This method does not need to be either safe or idempotent.
     * Operations requested through POST can have side effects for
     * which the user can be held accountable, for example,
     * updating stored data or buying items online.
     * <p/>
     * <p>If the HTTP POST request is incorrectly formatted,
     * <code>doPost</code> returns an HTTP "Bad Request" message.
     *
     * @param req  an {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet
     * @param resp an {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client
     * @throws java.io.IOException            if an input or output error is
     *                                        detected when the servlet handles
     *                                        the request
     * @throws javax.servlet.ServletException if the request for the POST
     *                                        could not be handled
     * @see javax.servlet.ServletOutputStream
     * @see javax.servlet.ServletResponse#setContentType
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a DELETE request.
     * <p/>
     * The DELETE operation allows a client to remove a document
     * or Web page from the server.
     * <p/>
     * <p>This method does not need to be either safe
     * or idempotent. Operations requested through
     * DELETE can have side effects for which users
     * can be held accountable. When using
     * this method, it may be useful to save a copy of the
     * affected URL in temporary storage.
     * <p/>
     * <p>If the HTTP DELETE request is incorrectly formatted,
     * <code>doDelete</code> returns an HTTP "Bad Request"
     * message.
     *
     * @param req  the {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client made of
     *             the servlet
     * @param resp the {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet returns
     *             to the client
     * @throws java.io.IOException            if an input or output error occurs
     *                                        while the servlet is handling the
     *                                        DELETE request
     * @throws javax.servlet.ServletException if the request for the
     *                                        DELETE cannot be handled
     */
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
    }
}
