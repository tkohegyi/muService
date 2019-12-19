package com.epam.wilma.domain.stubconfig.interceptor;
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

import com.epam.wilma.domain.stubconfig.parameter.ParameterList;

/**
 * Describes request and response interceptors given in the stub configuration.
 * @author Tunde_Kovacs
 *
 */
public class InterceptorDescriptor {

    private final String name;
    private final ExternalService externalService;
    private final ParameterList params;

    /**
     * Constructs a new instance of request interceptor descriptor.
     * @param name the name of the request interceptor
     * @param externalService the class used to intercept and process a request
     * @param params the list of parameters set in the stub configuration and used
     * by the request interceptor
     */
    public InterceptorDescriptor(final String name, final ExternalService externalService,
            final ParameterList params) {
        this.name = name;
        this.externalService = externalService;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public ExternalService getExternalService() {
        return externalService;
    }

    public ParameterList getParams() {
        return params;
    }

}
