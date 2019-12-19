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

import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Contains temporary configuration resources (externalServices).
 * This is needed in order to keep consistent state of the resource holder. During the config parsing,
 * inconsistent state of resources would be produced if some external classes would be loaded directly into
 * the resource holder object while some others are not yet validated.
 * @author Tunde_Kovacs, Tamas_Kohegyi
 *
 */
@Component
public class TemporaryConfigResourceHolder {

    private List<ExternalService> externalServices;

    /**
     * Adds a new <tt>requestInterceptor</tt> to the list of request interceptors.
     * @param externalService the {@link ExternalService} that will be added to the list
     */
    public void addRequestInterceptor(final ExternalService externalService) {
        if (externalServices != null) {
            externalServices.add(externalService);
        }
    }

    public List<ExternalService> getExternalServices() {
        return externalServices;
    }

    public void setExternalServices(final List<ExternalService> externalServices) {
        this.externalServices = externalServices;
    }

    /**
     * Empties the request interceptor list.
     */
    public void clearExternalServices() {
        if (externalServices != null) {
            externalServices.clear();
        }
    }

}
