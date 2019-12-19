package com.epam.wilma.stubconfig.dom.parser;
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

import com.epam.wilma.domain.stubconfig.StubResourceHolder;
import com.epam.wilma.domain.stubconfig.TemporaryConfigResourceHolder;
import com.epam.wilma.domain.stubconfig.helper.InternalResourceHolder;
import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Updates the {@link StubResourceHolder} from the {@link TemporaryConfigResourceHolder}.
 * @author Tunde_Kovacs
 *
 */
@Component
public class StubResourceHolderUpdater {

    @Autowired
    private StubResourceHolder stubResourceHolder;
    @Autowired
    private TemporaryConfigResourceHolder temporaryConfigResourceHolder;
    @Autowired
    private InternalResourceHolder internalResourceHolder;

    /**
     * Copies the resources from the {@link TemporaryConfigResourceHolder} to the {@link StubResourceHolder}.
     */
    public void updateResourceHolder() {
        updateRequestInterceptors();
    }

    private void updateRequestInterceptors() {
        List<ExternalService> externalServices = temporaryConfigResourceHolder.getExternalServices();
        if (externalServices != null) {
            stubResourceHolder.setExternalServices(new ArrayList<>(externalServices));
        }
    }

    /**
     * Clears the resources in the {@link TemporaryConfigResourceHolder}.
     */
    public void clearTemporaryResourceHolder() {
        temporaryConfigResourceHolder.clearExternalServices();
    }

    /**
     * Add the JSON Object to {@link StubResourceHolder}.
     * @param groupName is the group name attribute of stub configuration
     * @param jsonObject is the json object
     */
    public void addDocumentToResourceHolder(final String groupName, final JSONObject jsonObject) {
        stubResourceHolder.setActualConfigJsonObject(groupName, jsonObject);
    }

    /**
     * Copies internal services.
     * into the {@link TemporaryConfigResourceHolder}.
     */
    public void initializeTemporaryResourceHolder() {
        initializeExternalServices();
    }

    private void initializeExternalServices() {
        List<ExternalService> externalServices = internalResourceHolder.getExternalServices();
        if (externalServices != null) {
            temporaryConfigResourceHolder.setExternalServices(new ArrayList<>(externalServices));
        }
    }

}
