package com.epam.wilma.domain.stubconfig;
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

import com.epam.wilma.domain.stubconfig.interceptor.RequestInterceptor;
import com.epam.wilma.domain.stubconfig.interceptor.ResponseInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Contains temporary stub configuration resources (templates, response formatters, condition checkers, interceptors).
 * This is needed in order to keep consistent state of the resource holder. During the stub config parsing,
 * inconsistent state of resources would be produced if some external classes would be loaded directly into
 * the resource holder object while some others are not yet validated.
 * @author Tunde_Kovacs
 *
 */
@Component
public class TemporaryStubResourceHolder {

    private List<RequestInterceptor> requestInterceptors;
    private List<ResponseInterceptor> responseInterceptors;

    /**
     * Adds a new <tt>requestInterceptor</tt> to the list of request interceptors.
     * @param requestInterceptor the {@link RequestInterceptor} that will be added to the list
     */
    public void addRequestInterceptor(final RequestInterceptor requestInterceptor) {
        if (requestInterceptors != null) {
            requestInterceptors.add(requestInterceptor);
        }
    }

    public List<RequestInterceptor> getRequestInterceptors() {
        return requestInterceptors;
    }

    public void setRequestInterceptors(final List<RequestInterceptor> requestInterceptors) {
        this.requestInterceptors = requestInterceptors;
    }

    /**
     * Adds a new <tt>responseInterceptor</tt> to the list of response interceptors.
     * @param responseInterceptor the {@link ResponseInterceptor} that will be added to the list
     */
    public void addResponseInterceptor(final ResponseInterceptor responseInterceptor) {
        if (responseInterceptors != null) {
            responseInterceptors.add(responseInterceptor);
        }
    }

    public List<ResponseInterceptor> getResponseInterceptors() {
        return responseInterceptors;
    }

    public void setResponseInterceptors(final List<ResponseInterceptor> responseInterceptors) {
        this.responseInterceptors = responseInterceptors;
    }

    /**
     * Empties the request interceptor list.
     */
    public void clearRequestInterceptors() {
        if (requestInterceptors != null) {
            requestInterceptors.clear();
        }
    }

    /**
     * Empties the response interceptor list.
     */
    public void clearResponseInterceptors() {
        if (responseInterceptors != null) {
            responseInterceptors.clear();
        }
    }

}
