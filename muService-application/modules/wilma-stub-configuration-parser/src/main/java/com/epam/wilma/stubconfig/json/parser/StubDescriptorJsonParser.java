package com.epam.wilma.stubconfig.json.parser;

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
        JSONObject root = jsonStubDescriptor.getJSONObject("muServiceConfiguration");
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
        if (root.has("services")) {
            JSONArray interceptorArray = root.getJSONArray("services");
            for (int i = 0; i < interceptorArray.length(); i++) {
                InterceptorDescriptor interceptorDescriptor = interceptorDescriptorJsonParser.parseObject(interceptorArray.getJSONObject(i), root);
                interceptorDescriptors.add(interceptorDescriptor);
            }
        }
        return interceptorDescriptors;
    }
}
