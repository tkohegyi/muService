package com.epam.wilma.domain.stubconfig.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * External Service Interface.
 * @author Tamas_Kohegyi
 *
 */
public interface ExternalService {

    /**
     * Method that will be invoked, when the response is asked to be created.
     *
     * @param req              is the original request servlet
     * @param requestedService is the requested REST service part
     * @param resp             is the response servlet
     * @return with the body of the response, or null, if no response is provided.
     */
    String handleRequest(final HttpServletRequest req, final String requestedService, HttpServletResponse resp);

    /**
     * Get the list of REST services (Strings) when Wilma will call the handleRequest method.
     *
     * @return with the list of services provided by the class.
     */
    Set<String> getHandlers();

}
