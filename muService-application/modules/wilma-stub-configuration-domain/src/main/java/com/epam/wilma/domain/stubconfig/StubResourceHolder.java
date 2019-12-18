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
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains stub configuration resources (templates, template formatters, condition checkers,
 * interceptors and the previously parsed XML/JSON document).
 *
 * @author Tamas_Bihari
 * @author Tunde_Kovacs
 * @author Balazs_Berkes
 * @author Tamas_Kohegyi
 */
@Component
public class StubResourceHolder {

    private final Map<String, JSONObject> stubConfigJsonObjects;
    private List<RequestInterceptor> requestInterceptors;
    private List<ResponseInterceptor> responseInterceptors;

    /**
     * Default constructor for {@link StubResourceHolder} creation which initializes templates map.
     */
    public StubResourceHolder() {
        stubConfigJsonObjects = new HashMap<>();
    }

    public List<RequestInterceptor> getRequestInterceptors() {
        return requestInterceptors;
    }

    public void setRequestInterceptors(final List<RequestInterceptor> requestInterceptors) {
        this.requestInterceptors = requestInterceptors;
    }

    public List<ResponseInterceptor> getResponseInterceptors() {
        return responseInterceptors;
    }

    public void setResponseInterceptors(final List<ResponseInterceptor> responseInterceptors) {
        this.responseInterceptors = responseInterceptors;
    }


    /**
     * Get the JSON String of stub configuration from a Map.
     *
     * @param groupName is the group name of the selected stub configuration
     * @return the JSON object of the selected stub configuration
     */
    public JSONObject getActualStubConfigJsonObject(final String groupName) {
        return stubConfigJsonObjects.get(groupName);
    }

    /**
     * Put the String value of Json stub configuration into a Map with the give groupName.
     *
     * @param groupName  is the group name of the selected stub configuration
     * @param jsonObject is the string representation of the selected stub configuration
     */
    public void setActualStubConfigJsonObject(final String groupName, final JSONObject jsonObject) {
        stubConfigJsonObjects.put(groupName, jsonObject);
    }

}
