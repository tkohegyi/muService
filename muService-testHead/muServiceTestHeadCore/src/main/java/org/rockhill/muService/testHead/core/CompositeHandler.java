package org.rockhill.muService.testHead.core;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompositeHandler extends AbstractHandler {

    private AbstractHandler[] handlers;

    public CompositeHandler(AbstractHandler... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        for (AbstractHandler handler : handlers) {
            handler.handle(target, baseRequest, request, response);
        }
    }

}
