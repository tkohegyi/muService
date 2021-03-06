package com.epam.wilma.webapp.config.servlet.stub.resource;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.wilma.domain.stubconfig.StubResourcePathProvider;
import com.epam.wilma.webapp.config.servlet.helper.FileListJsonBuilder;
import com.epam.wilma.webapp.config.servlet.stub.helper.ResourceFileNameHandler;

/**
 * Provides unit tests for the class {@link StubResourceHandlerServlet}.
 * @author Tunde_Kovacs
 *
 */
public class StubResourceHandlerServletTest {

    private static final String PATH_INFO = "pathInfo";
    private static final String TYPE_PARAMETER = "type";
    private static final String TEMPLATE = "template";
    private static final String INTERCEPTOR = "interceptor";
    private static final String JAR = "jar";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ERROR_MESSAGE_TEMPLATE = "Invalid type %s ! Valid types are: %s, %s, %s, %s, %s and %s!";

    @Mock
    private FileListJsonBuilder fileListJsonBuilder;
    @Mock
    private StubResourcePathProvider stubResourcePathProvider;
    @Mock
    private ResourceFileNameHandler resourceFileNameHandler;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Path path;
    @Mock
    private PrintWriter writer;
    @Mock
    private File file;

    @InjectMocks
    private StubResourceHandlerServlet underTest;

    @BeforeMethod
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(underTest, "fileListJsonBuilder", fileListJsonBuilder);
        Whitebox.setInternalState(underTest, "stubResourcePathProvider", stubResourcePathProvider);
        Whitebox.setInternalState(underTest, "resourceFileNameHandler", resourceFileNameHandler);
        given(response.getWriter()).willReturn(writer);
    }

    @Test
    public void testDoGetWhenThereIsNoTypeParameterShouldWriteError() throws ServletException, IOException {
        //GIVEN
        given(request.getParameter(TYPE_PARAMETER)).willReturn(null);
        //WHEN
        underTest.doGet(request, response);
        //THEN
        verify(writer).write("Please provide a type parameter in the url!");
    }

    @Test
    public void testDoGetWhenTypeIsInterceptorShouldWriteResourceToResponse() throws ServletException, IOException {
        //GIVEN
        String expectedResult = "{\"files\":[\"Interceptor.class\"]}";
        given(request.getParameter(TYPE_PARAMETER)).willReturn(INTERCEPTOR);
        given(stubResourcePathProvider.getInterceptorPath()).willReturn(path);
        given(path.toFile()).willReturn(file);
        given(fileListJsonBuilder.buildFileListJson(file)).willReturn(expectedResult);
        //WHEN
        underTest.doGet(request, response);
        //THEN
        verify(writer).write(expectedResult);
        verify(response).setContentType(APPLICATION_JSON);
    }

    @Test
    public void testDoGetWhenTypeIsTemplateShouldWriteResourceToResponse() throws ServletException, IOException {
        //GIVEN
        String expectedResult = "{\"files\":[\"template.xml\"]}";
        given(request.getParameter(TYPE_PARAMETER)).willReturn(TEMPLATE);
        given(stubResourcePathProvider.getTemplatesPath()).willReturn(path);
        given(path.toFile()).willReturn(file);
        given(fileListJsonBuilder.buildFileListJson(file)).willReturn(expectedResult);
        //WHEN
        underTest.doGet(request, response);
        //THEN
        verify(writer).write(expectedResult);
    }

    @Test
    public void testDoGetWhenTypeIsJarShouldWriteResourceToResponse() throws ServletException, IOException {
        //GIVEN
        String expectedResult = "{\"files\":[\"something.jar\"]}";
        given(request.getParameter(TYPE_PARAMETER)).willReturn(JAR);
        given(stubResourcePathProvider.getJarPath()).willReturn(path);
        given(path.toFile()).willReturn(file);
        given(fileListJsonBuilder.buildFileListJson(file)).willReturn(expectedResult);
        //WHEN
        underTest.doGet(request, response);
        //THEN
        verify(writer).write(expectedResult);
    }

    @Test
    public void testDoGetWhenRequestPathInfoIsNullShouldCallHandleRequest() throws ServletException, IOException {
        //GIVEN
        given(request.getParameter(TYPE_PARAMETER)).willReturn(TEMPLATE);
        given(stubResourcePathProvider.getTemplatesPath()).willReturn(path);
        given(request.getPathInfo()).willReturn(PATH_INFO);
        //WHEN
        underTest.doGet(request, response);
        //THEN
        verify(resourceFileNameHandler).writeResourceFile(response, PATH_INFO, path);
    }

    @Test
    public void testDoPostWhenRequestPathInfoIsNullShouldCallHandleRequest() throws ServletException, IOException {
        //GIVEN
        given(request.getParameter(TYPE_PARAMETER)).willReturn(TEMPLATE);
        given(stubResourcePathProvider.getTemplatesPath()).willReturn(path);
        given(request.getPathInfo()).willReturn(PATH_INFO);
        //WHEN
        underTest.doPost(request, response);
        //THEN
        verify(resourceFileNameHandler).writeResourceFile(response, PATH_INFO, path);
    }
}
