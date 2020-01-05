package org.rockhill.muService.testHead.core.handlers;

import org.eclipse.jetty.server.Request;
import org.rockhill.muService.testHead.core.handlers.helper.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jetty handler that is able to handle requests coming to /getInformation endpoint.
 * @author Tamas_Kohegyi
 *
 */
public class ExampleHandler extends ResponseHandler {

    private static final String PATH_TO_HANDLE = "/getInformation";

    @Override
    public void handle(final String path, final Request baseRequest, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws IOException {
        if (PATH_TO_HANDLE.equals(path)) {
            //ok, send back the test server version
            String responseBody = "";
            responseBody = "{ "
                    + "\"id\": \"" + "self-id" + "\", "
                    + "\"software\": \"" + getClass().getPackage().getImplementationTitle() + "\", "
                    + "\"version\": \"" + getClass().getPackage().getImplementationTitle() + "\", "
                    + "\"capabilityCode\": \"" + "0" + "\", "
                    + "\"type\": \"" + "general-unspecified" + "\" "
                    + "}";
            prepareAnswer(baseRequest, httpServletRequest,httpServletResponse , responseBody);
        }
    }

}
