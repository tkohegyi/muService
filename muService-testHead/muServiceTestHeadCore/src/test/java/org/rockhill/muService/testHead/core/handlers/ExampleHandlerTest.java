package org.rockhill.muService.testHead.core.handlers;

import org.eclipse.jetty.server.Request;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link ExampleHandler}.
 * @author Tamas_Kohegyi
 *
 */
public class ExampleHandlerTest {

    private static final String PATH_TO_HANDLE = "/status";

    @InjectMocks
    private ExampleHandler underTest;

    @Mock
    private Request request;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private ServletInputStream requestBody;

    @Mock
    private InputStream responseBody;

    @Mock
    private PrintWriter writer;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = Mockito.spy(underTest);
    }

    @Test(enabled = false)
    public final void testHandleShouldReturnResponse() throws IOException, ServletException {
        // GIVEN
        given(httpServletResponse.getWriter()).willReturn(writer);
        // WHEN
        underTest.handle("/status", request, httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).setContentType(anyString());
        verify(httpServletResponse).setStatus(HttpServletResponse.SC_OK);
        verify(writer).println("server-response");
        verify(request).setHandled(true);
    }
}
