package org.rockhill.muService.serverService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UtilitiesTest {

    private Utilities underTest;

    @BeforeMethod
    public void setup() {
        underTest = new Utilities();
    }

    @Test
    public void testIsJsonMessage() throws Exception {
        //GIVEN
        //WHEN
        boolean jsonResult = underTest.isJsonMessage("{\"property\":\"value\"}");
        boolean jsonArrayResult = underTest.isJsonMessage("{\"list\":[{\"key\":\"value\"},{\"key\":\"value\"}]}");
        boolean xmlResult = underTest.isJsonMessage("<root><child></child></root>");
        boolean emptyResult = underTest.isJsonMessage("");
        boolean textResult = underTest.isJsonMessage("simple text");
        //THEN
        assertTrue(jsonResult);
        assertTrue(jsonArrayResult);
        assertFalse(xmlResult);
        assertFalse(emptyResult);
        assertFalse(textResult);
    }

}