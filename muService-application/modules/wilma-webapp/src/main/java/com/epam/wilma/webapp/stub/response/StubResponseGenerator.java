package com.epam.wilma.webapp.stub.response;

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

import com.epam.wilma.common.helper.StackTraceToStringConverter;
import com.epam.wilma.core.MapBasedResponseDescriptorAccess;
import com.epam.wilma.domain.http.WilmaHttpRequest;
import com.epam.wilma.domain.stubconfig.dialog.response.ResponseDescriptor;
import com.epam.wilma.domain.stubconfig.dialog.response.ResponseFormatterDescriptor;
import com.epam.wilma.router.domain.ResponseDescriptorDTO;
import com.epam.wilma.webapp.stub.response.support.HttpServletRequestTransformer;
import com.epam.wilma.webapp.stub.response.support.StubResponseHeaderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Generates the appropriate response for a request.
 * Gets the response descriptor data transfer object from the key-value store using the request's WilmaLoggerId and generates the response.
 * @author Tamas_Bihari
 *
 */
@Component
public class StubResponseGenerator {

    private final Logger logger = LoggerFactory.getLogger(StubResponseGenerator.class);
    @Autowired
    private MapBasedResponseDescriptorAccess mapBasedResponseDescriptorAccess;
    @Autowired
    private StackTraceToStringConverter stackTraceToStringConverter;
    @Autowired
    private StubResponseHeaderConfigurer stubResponseHeaderConfigurer;
    @Autowired
    private HttpServletRequestTransformer httpServletRequestTransformer;

    /**
     * Gets the response descriptor data transfer object from the key-value store using the request's WilmaLoggerId and generates the response.
     * @param req is the request for the response generation
     * @param resp is the response
     * @return with the generated response as byte array
     */
    public byte[] generateResponse(final HttpServletRequest req, final HttpServletResponse resp) {
        String wilmaLoggerId = req.getHeader(WilmaHttpRequest.WILMA_LOGGER_ID);
        byte[] result = null;
        if (wilmaLoggerId != null) {
            ResponseDescriptorDTO responseDescriptorDTO = mapBasedResponseDescriptorAccess.getResponseDescriptor(wilmaLoggerId);
            Set<ResponseFormatterDescriptor> responseFormatterDescriptors = responseDescriptorDTO.getResponseDescriptor().getResponseFormatters();
            //generate pure WilmaHttpRequest
            WilmaHttpRequest wilmaRequest = httpServletRequestTransformer.transformToWilmaHttpRequest(wilmaLoggerId, req, responseDescriptorDTO);
            //add wilma information to response header
            stubResponseHeaderConfigurer.addWilmaInfoToResponseHeader(req, resp, responseDescriptorDTO.getDialogDescriptorName());
            //set headers generate response body
            result = generate(resp, responseDescriptorDTO, responseFormatterDescriptors, wilmaRequest);
        }
        return result;
    }

    private byte[] generate(final HttpServletResponse resp, final ResponseDescriptorDTO responseDescriptorDTO,
            final Set<ResponseFormatterDescriptor> responseFormatterDescriptors, final WilmaHttpRequest wilmaRequest) {
        byte[] result;
        ResponseDescriptor responseDescriptor = responseDescriptorDTO.getResponseDescriptor();
        try {
            result = responseDescriptor.getAttributes().getTemplate().getResource();
            //set response status and content type
            stubResponseHeaderConfigurer.setResponseContentTypeAndStatus(resp, responseDescriptorDTO);
        } catch (Exception e) {
            stubResponseHeaderConfigurer.setErrorResponseContentTypeAndStatus(resp);
            result = getErrorMessageWithStackTrace(e);
        }
        return result;
    }

    private byte[] getErrorMessageWithStackTrace(final Exception e) {
        return stackTraceToStringConverter.getStackTraceAsString(e).getBytes();
    }

}
