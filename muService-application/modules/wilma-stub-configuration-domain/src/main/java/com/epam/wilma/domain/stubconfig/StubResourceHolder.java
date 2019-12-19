package com.epam.wilma.domain.stubconfig;

import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains configuration resources (external services and the previously parsed JSON document).
 *
 * @author Tamas_Kohegyi
 */
@Component
public class StubResourceHolder {

    private final Map<String, JSONObject> stubConfigJsonObjects;
    private List<ExternalService> externalServices;

    /**
     * Default constructor for {@link StubResourceHolder} creation which initializes templates map.
     */
    public StubResourceHolder() {
        stubConfigJsonObjects = new HashMap<>();
    }

    public List<ExternalService> getExternalServices() {
        return externalServices;
    }

    public void setExternalServices(final List<ExternalService> externalServices) {
        this.externalServices = externalServices;
    }

    /**
     * Get the JSON String of configuration from a Map.
     *
     * @param groupName is the group name of the selected configuration
     * @return the JSON object of the selected configuration
     */
    public JSONObject getActualConfigJsonObject(final String groupName) {
        return stubConfigJsonObjects.get(groupName);
    }

    /**
     * Put the String value of Json configuration into a Map with the give groupName.
     *
     * @param groupName  is the group name of the selected configuration
     * @param jsonObject is the string representation of the selected configuration
     */
    public void setActualConfigJsonObject(final String groupName, final JSONObject jsonObject) {
        stubConfigJsonObjects.put(groupName, jsonObject);
    }

}
