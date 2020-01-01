package org.rockhill.muService.testHead.core.handlers.helper;

import com.epam.wilma.test.server.compress.gzip.GzipCompressor;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Common method(s) of handler(s) in order to ease the preparation job of the responses.
 * @author Tamas_Kohegyi
 *
 */

public class ResponseHandler extends AbstractHandler {

    private static final String GZIP_TYPE = "gzip";
    private static final String ACCEPT_ENCODING = "accept-encoding";
    private static final String CONTENT_ENCODING = "content-encoding";


    private final GzipCompressor gzipCompressor = new GzipCompressor();

    protected void prepareAnswer(final Request baseRequest, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final String responseBody) throws IOException {
        byte[] responseBodyAsBytes = responseBody.getBytes();
        //Encodes response body with gzip if client accepts gzip encoding
        if (httpServletRequest.getHeader(ACCEPT_ENCODING) != null && httpServletRequest.getHeader(ACCEPT_ENCODING).contains(GZIP_TYPE)) {
            ByteArrayOutputStream gzipped = gzipCompressor.compress(new ByteArrayInputStream(responseBody.getBytes()));
            responseBodyAsBytes = gzipped.toByteArray();
            httpServletResponse.addHeader(CONTENT_ENCODING, GZIP_TYPE);
        }
        httpServletResponse.getOutputStream().write(responseBodyAsBytes);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

}
