package org.rockhill.muService.common;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilities {

    /**
     * Decides whether given {@code WilmaHttpRequest} is JSON or not.
     * @param requestBody request to check
     * @return {@code true} if the request is a JSON message otherwise {@code false}.
     */
    public boolean isJsonMessage(final String requestBody) {
        boolean isJson;
        try {
            new JSONObject(requestBody);
            isJson = true;
        } catch (JSONException ex) {
            isJson = false;
        }
        return isJson;
    }

}
