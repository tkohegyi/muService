package com.epam.wilma.stubconfig;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for ensuring the proper content of the Config Json Schema.
 *
 * @author Tamas_Kohegyi
 */
public class ServiceConfigurationJsonSchemaInterceptorTest extends ServiceConfigurationJsonSchemaTestBase {

    @BeforeClass
    public void setup() {
        setTestFilePath("schema/interceptors/");
        loadStubConfigJsonSchemaTest();
    }

    @Test
    public void testCheckValidInterceptors() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInterceptorsValid.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertTrue(matches);
    }

    @Test
    public void testCheckValidInterceptorsMinimal() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInterceptorsValidMinimal.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertTrue(matches);
    }

    @Test
    public void testCheckInValidInterceptorsMissingMandatoryName() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInValidInterceptorsMissingMandatoryName.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertFalse(matches);
    }

    @Test
    public void testCheckInValidInterceptorsMissingMandatoryClass() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInValidInterceptorsMissingMandatoryClass.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertFalse(matches);
    }

    @Test
    public void testCheckInValidInterceptorsMandatoryNameIsEmpty() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInValidInterceptorsMandatoryNameIsEmpty.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertFalse(matches);
    }

    @Test
    public void testCheckInValidInterceptorsMandatoryClassIsEmpty() throws IOException {
        String stubConfigRequest = givenStubConfigRequest("JsonConfigurationTestInValidInterceptorsMandatoryClassIsEmpty.json");

        boolean matches = checkConfigurationValidity(stubConfigRequest);

        assertFalse(matches);
    }

}
