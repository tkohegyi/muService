package com.epam.wilma.stubconfig.json.schema;

import com.epam.wilma.common.helper.ResourceLoader;
import com.epam.wilma.domain.stubconfig.exception.StubConfigJsonSchemaException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Used to parse the service config json schema.
 *
 * @author Tamas Kohegyi
 */
@Component
public class ServiceConfigurationJsonSchemaParser {

    @Autowired
    @Qualifier("serviceConfigurationJsonSchemaLocation")
    private String serviceConfigJsonSchemaLocation;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Loads the Json Schema of the stub config.
     *
     * @return the parsed Schema instance
     */
    public Schema parseSchema() {
        Schema result;
        try {
            URL schemaFile = resourceLoader.loadResource(serviceConfigJsonSchemaLocation);
            InputStream inputStream = schemaFile.openStream();
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            result = SchemaLoader.load(rawSchema);
        } catch (IOException e) {
            throw new StubConfigJsonSchemaException("Cannot load: " + serviceConfigJsonSchemaLocation
                    + " as Json Schema to check the Service Configuration, pls notify muService maintainers.");
        } catch (JSONException e) {
            throw new StubConfigJsonSchemaException("Parsing of stub config Json Schema failed, please notify muService maintainers.", e);
        }
        return result;
    }

}
