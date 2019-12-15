package com.epam.wilma.stubconfig.initializer.template;
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

import org.springframework.stereotype.Component;

/**
 * Class for reading template files from file system.
 * @author Tamas_Bihari
 *
 */
@Component
public class TemplateFileReader {

    /**
     * Read the specified template file from file system.
     * Throws StubConfigurationException if the template can not be read.
     * @param templatesName is the name of the template resource
     * @return with file as byte array
     */
    public byte[] readTemplate(final String templatesName) {
        return null;
    }

}
