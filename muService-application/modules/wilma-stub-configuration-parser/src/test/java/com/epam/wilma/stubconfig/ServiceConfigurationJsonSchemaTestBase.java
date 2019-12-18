package com.epam.wilma.stubconfig;

import org.apache.commons.io.IOUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Unit test for ensuring the proper content of the Config Json Schema.
 *
 * @author Tamas_Kohegyi
 */
public class ServiceConfigurationJsonSchemaTestBase {

    private String testFilePath;
    private Schema underTest;

    void setTestFilePath(String testFilePath) {
        this.testFilePath = testFilePath;
    }

    void loadStubConfigJsonSchemaTest() {
        String jsonSchemaPath = "../../../../muServiceConfigSchema.json";
        InputStream inputStream = getClass().getResourceAsStream(jsonSchemaPath);
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        underTest = SchemaLoader.load(rawSchema);
    }

    String givenStubConfigRequest(String jsonFileName) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(testFilePath + jsonFileName);
        return IOUtils.toString(inputStream);
    }

    boolean checkConfigurationValidity(String stubConfigRequest) {
        boolean result = true;

        JSONObject jsonToBeValidated = null;
        try {
            InputStream stream = new ByteArrayInputStream(stubConfigRequest.getBytes(StandardCharsets.UTF_8.name()));
            jsonToBeValidated = new JSONObject(new JSONTokener(stream));
        } catch (JSONException | IOException e) {
            //it is not a valid Json file
            result = false;
        }
        if (result) {
            try {
                underTest.validate(jsonToBeValidated);
            } catch (ValidationException e) {
                //it is not a good Json file
                result = false;
            }
        }
        return result;
    }
}
