package com.epam.wilma.webapp.config.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.wilma.common.helper.muService;
import com.epam.wilma.webapp.helper.UrlAccessLogMessageAssembler;

/**
 * Servlet for shutting down muService.
 */
@Component
public class ShutdownServlet extends HttpServlet {

    private static final String SHUTDOWN_MESSAGE = "Shutting down muService.";

    private final Logger logger = LoggerFactory.getLogger(ShutdownServlet.class);

    private final muService muService;
    private final UrlAccessLogMessageAssembler urlAccessLogMessageAssembler;

    /**
     * Constructor using spring framework to initialize the class.
     * @param muService is the Wilma service object
     * @param urlAccessLogMessageAssembler is used to log the url access event
     */
    @Autowired
    public ShutdownServlet(muService muService, UrlAccessLogMessageAssembler urlAccessLogMessageAssembler) {
        this.muService = muService;
        this.urlAccessLogMessageAssembler = urlAccessLogMessageAssembler;
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp);
        logger.info(urlAccessLogMessageAssembler.assembleMessage(req, SHUTDOWN_MESSAGE));
        muService.stop();
    }

    private void writeResponse(final HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(SHUTDOWN_MESSAGE);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
