package com.epam.wilma.webapp.stub.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for default dispatching the incoming requests.
 * @author Tamas_KOhegyi
 *
 */
@Component
public class DispatcherServlet extends HttpServlet {

    private static final String ERROR_MESSAGE_FOR_UNWANTED_REQUESTS = "muService has declined this request.";
    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Writes response to the {@link HttpServletResponse}'s {@link ServletOutputStream},
     * which is always an error message.
     * @param req is the {@link HttpServletRequest}
     * @param resp is the {@link HttpServletResponse}
     * @throws IOException when response can not be written to {@link ServletOutputStream}.
     */
    public void writeResponse(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        writeErrorResponse(resp);
        logger.warn("muService received an unwanted request from this address: " + req.getRemoteAddr());
    }

    private void writeErrorResponse(final HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        out.write(ERROR_MESSAGE_FOR_UNWANTED_REQUESTS.getBytes());
        out.close();
    }

}
