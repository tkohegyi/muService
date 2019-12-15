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

import java.util.List;

import com.epam.wilma.domain.stubconfig.dialog.DialogDescriptor;
import com.epam.wilma.domain.stubconfig.interceptor.InterceptorDescriptor;

/**
 * The stub configuration contains several {@link DialogDescriptor}s that describe a request-response pair
 * - with what kind of response the stub should answer in case of a specific request.
 * It also contains interceptors that can be configured for the stub and cass process
 * requests and responses.
 * @author Marton_Sereg
 * @author Tunde_Kovacs
 * @author Balazs_Berkes
 * @author Tamas_Kohegyi
 *
 */
public class StubDescriptor {

    private final StubDescriptorAttributes attributes;
    private final List<InterceptorDescriptor> interceptorDescriptors;

    /**
     * Constructs a new instance of {@link StubDescriptor}.
     * @param attributes includes the groupname attribute of stub configuration
     * @param interceptorDescriptors the list of request and response interceptors defined in the stub configuration
     */
    public StubDescriptor(final StubDescriptorAttributes attributes, final List<InterceptorDescriptor> interceptorDescriptors) {
        super();
        this.attributes = attributes;
        this.interceptorDescriptors = interceptorDescriptors;
    }

    public List<InterceptorDescriptor> getInterceptorDescriptors() {
        return interceptorDescriptors;
    }

    public StubDescriptorAttributes getAttributes() {
        return attributes;
    }

}
