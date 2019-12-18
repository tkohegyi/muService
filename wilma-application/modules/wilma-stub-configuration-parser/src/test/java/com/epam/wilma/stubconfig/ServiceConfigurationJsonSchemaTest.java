package com.epam.wilma.stubconfig;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Unit test for ensuring the proper content of the Config Json Schema.
 *
 * @author Tamas_Kohegyi
 */
public class ServiceConfigurationJsonSchemaTest extends ServiceConfigurationJsonSchemaTestBase {

    @BeforeClass
    public void setup() {
        setTestFilePath("");
        loadStubConfigJsonSchemaTest();
    }

    @Test
    public void testCheckValid() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestValid.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertTrue(matches);
    }

    @Test
    public void testCheckValidInterceptorsMinimal() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestValidMinimal.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertTrue(matches);
    }

}
