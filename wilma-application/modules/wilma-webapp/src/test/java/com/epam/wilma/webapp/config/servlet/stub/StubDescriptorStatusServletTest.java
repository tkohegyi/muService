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
import com.epam.wilma.domain.stubconfig.StubDescriptorAttributes;
import com.epam.wilma.domain.stubconfig.interceptor.InterceptorDescriptor;
import com.epam.wilma.router.RoutingService;
import org.mockito.*;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

/**
 * Provides unit tests for the class {@link StubDescriptorStatusServlet}.
 *
 * @author Tunde_Kovacs
 */
public class StubDescriptorStatusServletTest {

    private static final String TEST_GROUPNAME = "test";

    private List<InterceptorDescriptor> interceptorDescriptors;

    @Mock
    private RoutingService routingService;
    @Mock
    private StubDescriptor stubDescriptor;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter out;
    @Mock
    private InterceptorDescriptor interceptorDescriptor;

    @InjectMocks
    private StubDescriptorStatusServlet underTest;

    private StubDescriptorAttributes stubDescriptorAttributes = new StubDescriptorAttributes(TEST_GROUPNAME);

    @BeforeMethod
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(underTest, "routingService", routingService);
        interceptorDescriptors = new ArrayList<>();
        Map<String, StubDescriptor> stubDescriptors = new LinkedHashMap<>();
        stubDescriptors.put(TEST_GROUPNAME, stubDescriptor);
        Whitebox.setInternalState(routingService, "stubDescriptors", stubDescriptors);
        given(routingService.getStubDescriptors()).willReturn(stubDescriptors);
        given(stubDescriptor.getAttributes()).willReturn(stubDescriptorAttributes);
        given(stubDescriptor.getInterceptorDescriptors()).willReturn(interceptorDescriptors);
        given(response.getWriter()).willReturn(out);
        Whitebox.setInternalState(stubDescriptor, "attributes", stubDescriptorAttributes);
    }

    @Test
    public void testDoGetWhenThereAreNoInterceptorDescriptorsWriteAnEmptyList() throws ServletException, IOException {
        //GIVEN in setUp
        //WHEN
        underTest.doGet(request, response);
        //THEN
        InOrder order = Mockito.inOrder(out);
        order.verify(out).write("\"interceptorDescriptors\":[");
        order.verify(out).write("]");
    }

    @Test
    public void testDoGetWhenThereIsOneInterceptorDescriptors() throws ServletException, IOException {
        //GIVEN in setUp
        interceptorDescriptors.add(interceptorDescriptor);
        given(interceptorDescriptor.getName()).willReturn("InterceptorDescriptor1");
        //WHEN
        underTest.doGet(request, response);
        //THEN
        InOrder order = Mockito.inOrder(out);
        order.verify(out).write("\"interceptorDescriptors\":[");
        order.verify(out).write("{\"Name\": \"InterceptorDescriptor1\"}");
        order.verify(out).write("]");
    }

}
