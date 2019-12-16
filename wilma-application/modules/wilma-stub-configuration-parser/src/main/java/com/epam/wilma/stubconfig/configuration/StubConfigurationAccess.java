package com.epam.wilma.stubconfig.configuration;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.wilma.common.configuration.ConfigurationAccessBase;
import com.epam.wilma.domain.stubconfig.StubResourcePathProvider;
import com.epam.wilma.properties.PropertyHolder;

/**
 * Sets the module appropriate configurations.
 * @author Tamas_Bihari
 *
 */
@Component
public class StubConfigurationAccess implements ConfigurationAccessBase {

    @Autowired
    private PropertyHolder propertyHolder;
    @Autowired
    private StubResourcePathProvider stubResourcePathProvider;

    @Override
    public void loadProperties() {
    }

    /**
     * Reload the properties from wilma configuration and then set the internal resource paths.
     */
    public void setProperties() {
        String templatePath = propertyHolder.get("stub.template.path");
        String interceptorPath = propertyHolder.get("stub.interceptor.path");
        String jarPath = propertyHolder.get("stub.jar.path");
        String cachePath = propertyHolder.get("stub.descriptors.cache.path");
        stubResourcePathProvider.setTemplatesPath(templatePath);
        stubResourcePathProvider.setInterceptorPath(interceptorPath);
        stubResourcePathProvider.setJarPath(jarPath);
        stubResourcePathProvider.setCachePath(cachePath);
    }

}
