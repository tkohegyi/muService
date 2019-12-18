package com.epam.wilma.webapp.config.servlet.stub;
/*==========================================================================
Copyright since 2013, EPAM Systems

This file is part of Wilma.

Wilma is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Wilma is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Wilma.  If not, see <http://www.gnu.org/licenses/>.
===========================================================================*/

import com.epam.wilma.domain.stubconfig.StubDescriptor;
import com.epam.wilma.domain.stubconfig.interceptor.InterceptorDescriptor;
import com.epam.wilma.router.RoutingService;
import com.epam.wilma.webapp.config.servlet.stub.helper.ExpirationTimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Returns the list of dialog descriptors of the actual stub configuration and their usage.
 *
 * @author Tunde_Kovacs
 */
@Component
public class StubDescriptorStatusServlet extends HttpServlet {

    private final RoutingService routingService;

    /**
     * Constructor using spring framework to initialize the class.
     * @param routingService provides access to the routing service
     * @param expirationTimeProvider provides access to expiration info of the time based stub configurations
     */
    @Autowired
    public StubDescriptorStatusServlet(RoutingService routingService, ExpirationTimeProvider expirationTimeProvider) {
        this.routingService = routingService;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, StubDescriptor> stubDescriptors = routingService.getStubDescriptors();
        writeAllDialogDescriptors(out, stubDescriptors);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void writeAllDialogDescriptors(final PrintWriter out, final Map<String, StubDescriptor> stubDescriptors) {
        out.write("{\"configs\":[");
        Iterator<String> iterator = stubDescriptors.keySet().iterator();
        while (iterator.hasNext()) {
            out.write("{");
            String groupName = iterator.next();
            StubDescriptor stubDescriptor = stubDescriptors.get(groupName);
            writeInterceptorDescriptors(out, stubDescriptor);
            out.write("}");
            if (iterator.hasNext()) {
                out.write(",");
            }
        }
        out.write("]}");
    }

    private void writeInterceptorDescriptors(PrintWriter out, StubDescriptor stubDescriptor) {
        out.write("\"interceptorDescriptors\":[");
        List<InterceptorDescriptor> interceptorDescriptors = stubDescriptor.getInterceptorDescriptors();
        Iterator<InterceptorDescriptor> iterator = interceptorDescriptors.iterator();
        while (iterator.hasNext()) {
            writeInterceptorName(out, iterator);
            if (iterator.hasNext()) {
                out.write(",");
            }
        }
        out.write("]");
    }

    private void writeInterceptorName(PrintWriter out, Iterator<InterceptorDescriptor> iterator) {
        InterceptorDescriptor interceptorDescriptor = iterator.next();
        String name = interceptorDescriptor.getName();
        out.write("{\"Name\": \"" + name + "\"}");
    }

}
