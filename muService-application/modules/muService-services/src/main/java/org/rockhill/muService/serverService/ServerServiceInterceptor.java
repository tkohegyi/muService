package org.rockhill.muService.serverService;

import com.epam.wilma.domain.http.WilmaHttpRequest;
import com.epam.wilma.domain.stubconfig.interceptor.RequestInterceptor;
import com.epam.wilma.domain.stubconfig.parameter.ParameterList;
import com.epam.wilma.webapp.service.external.ExternalWilmaService;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author tkohegyi
 */
public class ServerServiceInterceptor extends ServerServiceCore implements RequestInterceptor, ExternalWilmaService {

    private static final String HANDLED_SERVICE = "/server-service";

    Utilities utilities = new Utilities();

    /**
     * ExternalWilmaService method implementation - entry point to handle the request by the external service.
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
        String response = "{ \"serverServiceCall\": \"" + myMethod + ":" + request + "\", \"ping\": \"OK\" }";
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //get command
        try {
            if ("POST".equalsIgnoreCase(myMethod)) {
                String requestBody = IOUtils.toString(httpServletRequest.getReader());
                response = workWithCommand(requestBody);
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

    @Override
    public void onRequestReceive(WilmaHttpRequest wilmaHttpRequest, ParameterList parameterList) {
    }
}
