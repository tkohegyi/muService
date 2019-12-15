package com.epam.wilma.stubconfig.json.parser;
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

import com.epam.wilma.domain.stubconfig.StubDescriptor;
import com.epam.wilma.domain.stubconfig.StubDescriptorAttributes;
import com.epam.wilma.domain.stubconfig.interceptor.InterceptorDescriptor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a new {@link StubDescriptor} model from a given JSON Object.
 *
 * @author Tamas_Kohegyi
 */
@Component
public class StubDescriptorJsonParser {

    @Autowired
    private InterceptorDescriptorJsonParser interceptorDescriptorJsonParser;

    /**
     * Builds a new {@link StubDescriptor} domain model from a given JSON Object.
     *
     * @param jsonStubDescriptor the JSON Object of the stub descriptor
     * @return the newly built StubDescriptor
     */
    public StubDescriptor parse(final JSONObject jsonStubDescriptor) {
        JSONObject root = jsonStubDescriptor.getJSONObject("wilmaStubConfiguration");
        StubDescriptorAttributes attributes = getStubDescriptorAttributes(root);
        List<InterceptorDescriptor> interceptorDescriptors = getInterceptorDescriptors(root);
        //set validity - it is valid only if it has something inside
        if (interceptorDescriptors.size() > 0) {
            attributes.setValid(true);
        }
        StubDescriptor stubDescriptor = new StubDescriptor(attributes, interceptorDescriptors);
        return stubDescriptor;
    }

    private StubDescriptorAttributes getStubDescriptorAttributes(final JSONObject root) {
        String groupName = root.has("groupName") ? root.getString("groupName") : "Default";
        String activeText = root.has("active") ? root.getString("active") : "true";
        boolean active;
        active = Boolean.valueOf(activeText);
        return new StubDescriptorAttributes(groupName, active);
    }

    private List<InterceptorDescriptor> getInterceptorDescriptors(final JSONObject root) {
        List<InterceptorDescriptor> interceptorDescriptors = new ArrayList<>();
        if (root.has("interceptors")) {
            JSONArray interceptorArray = root.getJSONArray("interceptors");
            for (int i = 0; i < interceptorArray.length(); i++) {
                InterceptorDescriptor interceptorDescriptor = interceptorDescriptorJsonParser.parseObject(interceptorArray.getJSONObject(i), root);
                interceptorDescriptors.add(interceptorDescriptor);
            }
        }
        return interceptorDescriptors;
    }
}
