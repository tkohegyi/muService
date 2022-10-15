package website.magyar.muservice.web.helper;

import website.magyar.muservice.web.controller.helper.ControllerBase;

/**
 * Unit test for {@link ControllerBase} class.
 */

public class MockControllerBase extends ControllerBase {
    public String mockGetJsonString(String id, Object object) {
        return getJsonString(id, object);
    }

}
