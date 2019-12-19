package org.rockhill.muService.authenticatorService;

import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.rockhill.muService.common.Utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author tkohegyi
 */
public class AuthenticatorService extends AuthenticatorServiceCore implements ExternalService {

    private static final String HANDLED_SERVICE = "/authenticator-service";

    Utilities utilities = new Utilities();

    /**
     * ExternalService method implementation - entry point to handle the request by the external service.
     *
     * @param httpServletRequest  is the original request
     * @param request             is the request string itself (part of the URL, focusing on the requested service)
     * @param httpServletResponse is the response object
     * @return with the body of the response (need to set response code in httpServletResponse object)
     */
    @Override
    public String handleRequest(HttpServletRequest httpServletRequest, String request, HttpServletResponse httpServletResponse) {
        String myMethod = httpServletRequest.getMethod();
        String myService = (this.getClass().getSimpleName() + HANDLED_SERVICE).toLowerCase();
        boolean myCall = request.toLowerCase().startsWith(myService);

        //set default response
        String response = "{ \"authenticatorServiceCall\": \"" + myMethod + ":" + request + "\", \"ping\": \"OK\" }";
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //get command
        try {
            if (myCall && "POST".equalsIgnoreCase(myMethod)) {
                String requestBody = IOUtils.toString(httpServletRequest.getReader());
                if (utilities.isJsonMessage(requestBody)) {
                    response = workWithCommand(new JSONObject(requestBody));
                }
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (IOException e) {
            //nothing to do
        }
        return response;
    }

    /**
     * ExternalWilmaService method implementation - provides the list of requests, this service will handle.
     *
     * @return with the set of handled services.
     */
    @Override
    public Set<String> getHandlers() {
        return Sets.newHashSet(
                this.getClass().getSimpleName() + HANDLED_SERVICE
        );
    }

}
