package com.epam.wilma.stubconfig.json;

import com.epam.wilma.domain.stubconfig.StubDescriptor;
import com.epam.wilma.domain.stubconfig.StubDescriptorAttributes;
import com.epam.wilma.domain.stubconfig.exception.DescriptorCannotBeParsedException;
import com.epam.wilma.domain.stubconfig.exception.StubConfigJsonSchemaException;
import com.epam.wilma.stubconfig.ConfigurationDescriptorJsonFactory;
import com.epam.wilma.stubconfig.configuration.StubConfigurationAccess;
import com.epam.wilma.stubconfig.dom.parser.StubResourceHolderUpdater;
import com.epam.wilma.stubconfig.json.parser.ConfigurationDescriptorJsonParser;
import com.epam.wilma.stubconfig.json.schema.ServiceConfigurationJsonSchemaParser;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Loads the stub descriptor object model from an {@link InputStream} by reading it to a JSON Object and
 * then validating and parsing it to the proper object structure.
 *
 * @author Tamas Kohegyi
 */
@Component
public class JsonBasedConfigurationDescriptorFactory implements ConfigurationDescriptorJsonFactory {

    @Autowired
    private ConfigurationDescriptorJsonParser descriptorParser;
    @Autowired
    private StubResourceHolderUpdater stubResourceHolderUpdater;
    @Autowired
    private StubConfigurationAccess configurationAccess;
    @Autowired
    private ServiceConfigurationJsonSchemaParser serviceConfigurationJsonSchemaParser;

    /**
     * Loads the stub descriptor from an {@link InputStream}.
     * It is synchronized in order to avoid inconsistent states during an on the fly stub
     * configuration.
     *
     * @param inputStream the stream that contains the stub descriptor.
     * @return the newly built {@link StubDescriptor}
     */
    @Override
    public synchronized StubDescriptor buildStubDescriptor(final InputStream inputStream) {
        try {
            //load the json file
            JSONObject jsonStubDescriptor = new JSONObject(new JSONTokener(inputStream));
            //load the json schema file
            Schema jsonSchema = serviceConfigurationJsonSchemaParser.parseSchema();
            //validate against schema
            jsonSchema.validate(jsonStubDescriptor);
            //validate against extra rules (those cannot be im schema)
            extraValidation(jsonStubDescriptor);
            //if everything goes well, continue with registering the stub configuration
            stubResourceHolderUpdater.initializeTemporaryResourceHolder();
            configurationAccess.setProperties();
            StubDescriptor stubDescriptor = descriptorParser.parse(jsonStubDescriptor);
            stubResourceHolderUpdater.updateResourceHolder();
            stubResourceHolderUpdater.clearTemporaryResourceHolder();
            StubDescriptorAttributes attributes = stubDescriptor.getAttributes();
            stubResourceHolderUpdater.addDocumentToResourceHolder(attributes.getGroupName(), jsonStubDescriptor);
            return stubDescriptor;
        } catch (ValidationException e) {
            String errorMessage = "Stub descriptor cannot be parsed, reason: " + e.getMessage();
            throw new DescriptorCannotBeParsedException(errorMessage, e);
        } catch (JSONException | StubConfigJsonSchemaException e) {
            String errorMessage = "Stub descriptor cannot be parsed.";
            throw new DescriptorCannotBeParsedException(errorMessage, e);
        }
    }

    private void checkUniqueName(final String key, final JSONObject root, final String errorText) {
        if (root.has(key)) {
            JSONArray objectArray = root.getJSONArray(key);
            Set<String> set = new HashSet<>();
            for (int i = 0; i < objectArray.length(); i++) {
                set.add(objectArray.getJSONObject(i).getString("name"));
            }
            if (set.size() != objectArray.length()) {
                throw new StubConfigJsonSchemaException("Name duplication found at " + errorText);
            }
        }
    }

    private void extraValidation(final JSONObject jsonStubDescriptor) {
        JSONObject root = jsonStubDescriptor.getJSONObject("muServiceConfiguration");
        String key;
        // - name in services array must be unique
        checkUniqueName("services", root, "Services");
        // - configuration groupName shall not contain chars: "|" ";"
        key = "groupName";
        if (root.has(key)) {
            String name = root.getString(key);
            if (name.contains("|") || name.contains(";")) {
                throw new StubConfigJsonSchemaException("Stub Descriptor groupName='"
                        + name + "' contains invalid character ('|' or ';')");
            }
        }
        // - all class name must be valid/loadable
        // -> this must be validated during the parse action of the specific object
    }

}
